package com.homework.booking.controller;

import com.homework.booking.dto.PhotographerDTO;
import com.homework.booking.mapper.PhotographerMapper;
import com.homework.booking.model.Photographer;
import com.homework.booking.service.PhotographerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/photographers")
@RequiredArgsConstructor
public class PhotographerController {

    private final PhotographerService photographerService;
    private final PhotographerMapper photographerMapper;

    @GetMapping
    public ResponseEntity<List<PhotographerDTO>> getPhotographers() {
        List<Photographer> photographerEntities = photographerService.getPhotographers();

        List<PhotographerDTO> photographerDTOs = photographerEntities.stream()
                .map(photographerMapper::convertToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(photographerDTOs);
    }

    @PostMapping
    public ResponseEntity<List<PhotographerDTO>> savePhotographers(@RequestBody List<PhotographerDTO> photographerDTOs) {
        List<Photographer> photographerEntities = photographerDTOs.stream()
                .map(photographerMapper::convertToEntity)
                .collect(Collectors.toList());
        List<Photographer> savedPhotographers = photographerService.savePhotographers(photographerEntities);

        List<PhotographerDTO> photographerDTOsSaved = savedPhotographers.stream()
                .map(photographerMapper::convertToDTO)
                .collect(Collectors.toList());

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();

        return ResponseEntity.created(location)
                .body(photographerDTOsSaved);
    }


}
