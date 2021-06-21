package com.homework.booking.exception;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import static com.homework.booking.service.DateTimeService.DATE_TIME_PATTERN;

@Data
public class ExceptionResponseBody {

    public ExceptionResponseBody(String errorMsg) {
        this.errorMsg = errorMsg;
        timestampUTC =  DateTimeFormatter.ofPattern(DATE_TIME_PATTERN).format(LocalDateTime.now(ZoneOffset.UTC));
    }

    private String errorMsg;
    private String timestampUTC;

}
