package com.homework.booking.mapper;

import com.homework.booking.dto.PhotographerDTO;
import com.homework.booking.model.Photographer;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PhotographerMapper {

    private final ModelMapper modelMapper;

    //TODO Catch Parse Exception
    public Photographer convertToEntity(PhotographerDTO dto) {
        return modelMapper.map(dto, Photographer.class);
    }

    public PhotographerDTO convertToDTO(Photographer entity) {
        return modelMapper.map(entity, PhotographerDTO.class);
    }


}
