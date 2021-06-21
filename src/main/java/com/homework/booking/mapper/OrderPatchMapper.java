package com.homework.booking.mapper;

import com.homework.booking.dto.OrderDTO;
import com.homework.booking.exception.ValidationFailureException;
import com.homework.booking.model.Order;
import com.homework.booking.model.OrderStatus;
import com.homework.booking.model.PhotoType;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;

import static com.homework.booking.dto.OrderDTO.ORDER_STATUS_FIELD_NAME;
import static com.homework.booking.dto.OrderDTO.PHOTO_TYPE_FIELD_NAME;

@Component
public class OrderPatchMapper {

    public static final String TRANSITION_VIOLATION = "Transition from status %s to status %s is not possible!";

    public void mapPatchPropertiesToOrder(Order order, Map<String, Object> orderProperties) {
        orderProperties.entrySet().stream()
                .filter(e -> !OrderDTO.getNonEditableFields().contains(e.getKey()))
                .forEach(e -> {
                    Field field = ReflectionUtils.findField(Order.class, e.getKey());
                    if (field == null) {
                        throw new ValidationFailureException(String.format("%s is not a valid property!", e.getKey()));
                    } else {
                        field.setAccessible(true);
                    }

                    switch (field.getName()) {
                        case PHOTO_TYPE_FIELD_NAME:
                            order.setPhotoType(PhotoType.fromText(e.getValue().toString()));
                            break;
                        case ORDER_STATUS_FIELD_NAME:
                            OrderStatus newOrderStatus = OrderStatus.fromText(e.getValue().toString());
                            //We allow changing the status directly for Canceling the Order
                            if (newOrderStatus == OrderStatus.CANCELED) {
                                order.setOrderStatus(newOrderStatus);
                                //We allow and Uploaded Order to move to Assigned or Completed state
                            } else if (order.getOrderStatus() == OrderStatus.UPLOADED
                                    && Arrays.asList(OrderStatus.COMPLETED, OrderStatus.ASSIGNED).contains(newOrderStatus)) {
                                order.setOrderStatus(newOrderStatus);
                            } else if (newOrderStatus != null) {
                                throw new ValidationFailureException(
                                        String.format(TRANSITION_VIOLATION, order.getOrderStatus(), newOrderStatus));
                            }
                            break;
                        default:
                            ReflectionUtils.setField(field, order, e.getValue());
                    }
                });
    }

}
