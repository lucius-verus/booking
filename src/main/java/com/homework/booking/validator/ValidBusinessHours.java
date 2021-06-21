package com.homework.booking.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DateTimeValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidBusinessHours {

    String message() default "Hour must be between 8:00 and 20:00";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
