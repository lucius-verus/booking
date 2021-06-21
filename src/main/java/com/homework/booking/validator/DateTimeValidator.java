package com.homework.booking.validator;

import com.homework.booking.exception.ValidationFailureException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

public class DateTimeValidator implements ConstraintValidator<ValidBusinessHours, LocalDateTime> {

    @Override
    public boolean isValid(LocalDateTime value, ConstraintValidatorContext context) {
        //DateTime is not a mandatory field
        if (value == null)
            return true;
        else
            return isInsideBusinessHours(value, false);
    }

    public static boolean isInsideBusinessHours(LocalDateTime value, boolean throwIfInvalid) {
        boolean isValid = value.getHour() >= 8 && value.getHour() <= 20;
        if (throwIfInvalid && !isValid) {
            throw new ValidationFailureException("Hour must be between 8:00 and 20:00");
        } else {
            return isValid;
        }
    }

}
