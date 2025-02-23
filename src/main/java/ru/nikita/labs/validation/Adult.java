package ru.nikita.labs.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AdultValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Adult {

    String message();

    int age() default 18;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
