package com.homework.booking.service;

import com.homework.booking.exception.ResourceNotFoundException;
import com.homework.booking.exception.ValidationFailureException;
import com.homework.booking.mapper.OrderPatchMapper;
import com.homework.booking.model.Order;
import com.homework.booking.model.OrderStatus;
import com.homework.booking.model.Photographer;
import com.homework.booking.repository.OrderRepository;
import com.homework.booking.repository.PhotographerRepository;
import com.homework.booking.validator.DateTimeValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Map;

import static com.homework.booking.dto.OrderDTO.DATE_TIME_FIELD_NAME;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final PhotographerRepository photographerRepository;
    private final DateTimeService dateTimeService;
    private final OrderPatchMapper orderPatchMapper;

    public Order getOrder(Long id) {
        return orderRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Order with id = %s was not found!", id)));
    }

    public Order createOrder(Order order) {
        if (order.getDateTime() == null) {
            order.setOrderStatus(OrderStatus.UNSCHEDULED);
        } else {
            order.setOrderStatus(OrderStatus.PENDING);
        }

        return orderRepository.save(order);
    }

    /**
     * Generic method to partially update an existing Order
     *
     * @param orderId         id of Order to be patched
     * @param orderProperties key value paris with the fields that have to be changed
     * @return updated Order
     */
    @Transactional
    public Order patchOrder(Long orderId, Map<String, Object> orderProperties) {
        Order existingOrder = getOrder(orderId);

        if (OrderStatus.CANCELED.equals(existingOrder.getOrderStatus())) {
            throw new ValidationFailureException("Editing Canceled Orders is not allowed!");
        }

        //If DateTime is given and orderStatus is UNSCHEDULED -> move to PENDING
        if (existingOrder.getOrderStatus().equals(OrderStatus.UNSCHEDULED) && orderProperties.containsKey(DATE_TIME_FIELD_NAME)) {
            LocalDateTime dateTime = dateTimeService.parse(orderProperties.get(DATE_TIME_FIELD_NAME).toString());
            DateTimeValidator.isInsideBusinessHours(dateTime, true);
            existingOrder.setDateTime(dateTime);
            existingOrder.setOrderStatus(OrderStatus.PENDING);
        }

        orderPatchMapper.mapPatchPropertiesToOrder(existingOrder, orderProperties);

        return orderRepository.save(existingOrder);
    }


    @Transactional
    public Order assignPhotographerToOrder(Long orderId, Long photographerId) {
        Order order = getOrder(orderId);

        if (!OrderStatus.PENDING.equals(order.getOrderStatus())) {
            throw new ValidationFailureException("In order to Assign a Photographer, the Order status has to be in status PENDING!");
        }

        Photographer photographer = photographerRepository.findById(photographerId).orElseThrow(() ->
                new ResourceNotFoundException(String.format("Photographer with id = %s was not found!", photographerId)));
        order.setPhotographer(photographer);
        order.setOrderStatus(OrderStatus.ASSIGNED);

        return orderRepository.save(order);
    }

    public String getPhotos(Long orderId) {
        Order order = getOrder(orderId);

        if (order.getPhotos() == null) {
            throw new ValidationFailureException("No Photos were found for this Order!");
        } else {
            return order.getPhotos();
        }
    }

    @Transactional
    public void uploadPhotos(Long orderId, String zip) {
        Order order = getOrder(orderId);
        if (!OrderStatus.ASSIGNED.equals(order.getOrderStatus())) {
            throw new ValidationFailureException("In order to Upload Photos, the Order status has to be in status ASSIGNED!");
        }

        order.setPhotos(zip);
        order.setOrderStatus(OrderStatus.UPLOADED);

        orderRepository.save(order);
    }
}
