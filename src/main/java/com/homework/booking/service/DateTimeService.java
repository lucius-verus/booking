package com.homework.booking.service;

import com.homework.booking.exception.ValidationFailureException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Service
public class DateTimeService {

    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

    public LocalDateTime parse(String date) {
        try {
            return LocalDateTime.parse(date, DATE_TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new ValidationFailureException("DateTime field is invalid! Please make sure the format is " + DATE_TIME_PATTERN);
        }
    }

}
