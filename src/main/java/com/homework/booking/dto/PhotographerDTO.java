package com.homework.booking.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PhotographerDTO {

    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

}
