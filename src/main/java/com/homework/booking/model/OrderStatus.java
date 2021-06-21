package com.homework.booking.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.homework.booking.exception.ValidationFailureException;
import org.apache.logging.log4j.util.Strings;

import java.util.Arrays;

public enum OrderStatus {
    @JsonProperty("Unscheduled")
    UNSCHEDULED("Unscheduled"),
    @JsonProperty("Pending")
    PENDING("Pending"),
    @JsonProperty("Assigned")
    ASSIGNED("Assigned"),
    @JsonProperty("Uploaded")
    UPLOADED("Uploaded"),
    @JsonProperty("Completed")
    COMPLETED("Completed"),
    @JsonProperty("Canceled")
    CANCELED("Canceled");

    private final String status;

    OrderStatus(String status) {
        this.status = status;
    }

    @JsonCreator
    public static OrderStatus fromText(String status) {
        if (Strings.isBlank(status))
            return null;

        return Arrays.stream(values())
                .filter(photoType -> photoType.status.equalsIgnoreCase(status))
                .findFirst()
                .orElseThrow(() -> new ValidationFailureException("No OrderStatus enum constant found for value = " + status));
    }
}
