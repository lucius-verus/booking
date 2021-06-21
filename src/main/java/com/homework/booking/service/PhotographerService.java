package com.homework.booking.service;

import com.homework.booking.model.Photographer;
import com.homework.booking.repository.PhotographerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PhotographerService {

    private final PhotographerRepository photographerRepository;

    public List<Photographer> getPhotographers() {
        return photographerRepository.findAll();
    }

    public List<Photographer> savePhotographers(List<Photographer> photographers) {
        return photographerRepository.saveAll(photographers);
    }

}
