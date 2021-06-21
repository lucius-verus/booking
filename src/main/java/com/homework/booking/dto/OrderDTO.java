package com.homework.booking.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.homework.booking.model.OrderStatus;
import com.homework.booking.model.PhotoType;
import com.homework.booking.model.Photographer;
import com.homework.booking.validator.ValidBusinessHours;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(value = {"id", "photographer", "photos"}, allowGetters = true)
public class OrderDTO {

    public static final String ID_FIELD_NAME = "id";
    public static final String DATE_TIME_FIELD_NAME = "dateTime";
    public static final String PHOTO_TYPE_FIELD_NAME = "photoType";
    public static final String ORDER_STATUS_FIELD_NAME = "orderStatus";
    public static final String PHOTOGRAPHER_FIELD_NAME = "photographer";

    private Long id;

    @NotBlank
    private String name;
    @NotBlank
    private String surName;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String cellNumber;

    private PhotoType photoType;
    private String title;
    private String logisticInfo;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    @ValidBusinessHours
    private LocalDateTime dateTime;
    private OrderStatus orderStatus;
    private Photographer photographer;

    /**
     * @return list of field that should not be edited AFTER they have been given values
     */
    public static List<String> getNonEditableFields(){
        return Arrays.asList(ID_FIELD_NAME, DATE_TIME_FIELD_NAME, PHOTOGRAPHER_FIELD_NAME);
    }

}
