package ru.nikita.labs.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.Period;

public class AdultValidator implements ConstraintValidator<Adult, LocalDate> {

    private int age;

    @Override
    public void initialize(Adult constraintAnnotation) {
        this.age = constraintAnnotation.age();
    }

    @Override
    public boolean isValid(LocalDate dateOfBirth,
                           ConstraintValidatorContext context) {
        if (dateOfBirth == null) {
            return false;
        }
        LocalDate now = LocalDate.now();
        Period period = Period.between(dateOfBirth, now);
        return period.getYears() >= age;
    }
}