package ru.nikita.labs.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CrudException extends RuntimeException {
    protected HttpStatus errorCode;

    public CrudException(String message,
                         HttpStatus errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
