package org.example.lunchvote.util.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = UpTo11ClockValidator.class)
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface UpTo11Clock {
    String message() default "Голосование разрешено только до 11 часов!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
