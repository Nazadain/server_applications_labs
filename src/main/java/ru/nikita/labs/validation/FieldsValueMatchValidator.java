package ru.nikita.labs.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanUtils;

public class FieldsValueMatchValidator
        implements ConstraintValidator<FieldsValueMatch, Object> {

    private String field;
    private String fieldMatch;

    @Override
    public void initialize(FieldsValueMatch constraintAnnotation) {
        this.field = constraintAnnotation.field();
        this.fieldMatch = constraintAnnotation.fieldMatch();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        try {
            Object fieldValue = BeanUtils
                    .getPropertyDescriptor(value.getClass(), field)
                    .getReadMethod()
                    .invoke(value);
            Object fieldMatchValue = BeanUtils
                    .getPropertyDescriptor(value.getClass(), fieldMatch)
                    .getReadMethod()
                    .invoke(value);

            if (fieldValue == null) {
                return fieldMatchValue == null;
            }
            return fieldValue.equals(fieldMatchValue);
        } catch (Exception e) {
            return false;
        }
    }
}
