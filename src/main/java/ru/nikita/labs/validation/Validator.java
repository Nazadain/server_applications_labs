package ru.nikita.labs.validation;

public interface Validator<T> {
    void validate(T body) throws RuntimeException;
}
