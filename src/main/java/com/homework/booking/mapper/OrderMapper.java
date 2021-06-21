package com.homework.booking.mapper;

import com.homework.booking.dto.OrderDTO;
import com.homework.booking.model.Order;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final ModelMapper modelMapper;

    public Order convertToEntity(OrderDTO dto) {
        return modelMapper.map(dto, Order.class);
    }

    public OrderDTO convertToDTO(Order entity) {
        return modelMapper.map(entity, OrderDTO.class);
    }


}
