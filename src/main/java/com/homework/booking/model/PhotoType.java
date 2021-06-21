package com.homework.booking.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.homework.booking.exception.ValidationFailureException;
import org.apache.logging.log4j.util.Strings;

import java.util.Arrays;

public enum PhotoType {
    @JsonProperty("Real Estate")
    REAL_ESTATE("Real Estate"),
    @JsonProperty("Food")
    FOOD("Food"),
    @JsonProperty("Events")
    EVENTS("Events");

    private final String type;

    PhotoType(String type) {
        this.type = type;
    }

    @JsonCreator
    public static PhotoType fromText(String type) {
        if (Strings.isBlank(type))
            return null;

        return Arrays.stream(values())
                .filter(photoType -> photoType.type.equalsIgnoreCase(type))
                .findFirst()
                .orElseThrow(() -> new ValidationFailureException("No PhotoType enum constant found for value = " + type));
    }
}
