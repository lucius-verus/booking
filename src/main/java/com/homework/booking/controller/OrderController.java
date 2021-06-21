package com.homework.booking.controller;

import com.homework.booking.dto.OrderDTO;
import com.homework.booking.mapper.OrderMapper;
import com.homework.booking.model.Order;
import com.homework.booking.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrder(@PathVariable Long id) {
        Order order = orderService.getOrder(id);

        return ResponseEntity.ok(orderMapper.convertToDTO(order));
    }

    @PostMapping
    public ResponseEntity<OrderDTO> addOrder(@Valid @RequestBody OrderDTO orderDTO) {
        Order order = orderMapper.convertToEntity(orderDTO);
        Order orderSaved = orderService.createOrder(order);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(orderSaved.getId()).toUri();

        return ResponseEntity.created(location)
                .body(orderMapper.convertToDTO(orderSaved));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<OrderDTO> patchOrder(@PathVariable("id") Long orderId,
                                               @RequestBody Map<String, Object> fields) {
        Order order = orderService.patchOrder(orderId, fields);

        return ResponseEntity.ok(orderMapper.convertToDTO(order));
    }


    @PatchMapping("/{id}/photographer/{pid}")
    public ResponseEntity<OrderDTO> assignPhotographerToOrder(@PathVariable("id") Long orderId,
                                                              @PathVariable("pid") Long photographerId) {
        Order order = orderService.assignPhotographerToOrder(orderId, photographerId);

        return ResponseEntity.ok(orderMapper.convertToDTO(order));
    }


    @GetMapping("/{id}/photos")
    public ResponseEntity<String> getPhotos(@PathVariable("id") Long orderId) {
        return  ResponseEntity.ok(orderService.getPhotos(orderId));
    }

    @PostMapping("/{id}/photos")
    public ResponseEntity<String> uploadPhotos(@PathVariable("id") Long orderId,
                                               @RequestParam String photos) {
        orderService.uploadPhotos(orderId, photos);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}/photos")
                .buildAndExpand(orderId)
                .toUri();

        return ResponseEntity.created(location).body("Photos were successfully uploaded!");
    }

}
