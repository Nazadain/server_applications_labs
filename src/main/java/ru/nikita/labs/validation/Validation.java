package ru.nikita.labs.validation;

public interface Validation<T> {
    void validate(T body) throws RuntimeException;
}
