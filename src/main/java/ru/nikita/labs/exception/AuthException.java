package ru.nikita.labs.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import ru.nikita.labs.exception.message.AuthMessage;

@Getter
public class AuthException extends RuntimeException {
    private HttpStatus errorCode;

    public AuthException(AuthMessage message) {
        super(message.getMessage());
    }

    public AuthException(AuthMessage message, HttpStatus errorCode) {
        super(message.getMessage());
        this.errorCode = errorCode;
    }
}
