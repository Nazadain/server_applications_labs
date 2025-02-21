package ru.nikita.labs.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import ru.nikita.labs.exception.message.AuthMessage;
import ru.nikita.labs.exception.message.ValidationMessage;

@Getter
public class ValidationException extends RuntimeException {
    private HttpStatus errorCode;

    public ValidationException(ValidationMessage message) {
        super(message.getMessage());
    }

    public ValidationException(HttpStatus errorCode) {
        this.errorCode = errorCode;
    }

    public ValidationException(ValidationMessage message,
                               HttpStatus errorCode) {
        super(message.getMessage());
        this.errorCode = errorCode;
    }
}
