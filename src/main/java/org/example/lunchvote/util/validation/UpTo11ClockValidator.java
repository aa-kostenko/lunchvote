package org.example.lunchvote.util.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

public class UpTo11ClockValidator implements ConstraintValidator<UpTo11Clock, LocalDateTime> {
    @Override
    public boolean isValid(LocalDateTime value, ConstraintValidatorContext context) {
        return value.getHour() < 11;
    }
}
