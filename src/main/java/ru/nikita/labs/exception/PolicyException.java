package ru.nikita.labs.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import ru.nikita.labs.exception.message.PolicyMessage;

@Getter
public class PolicyException extends CrudException {
    public PolicyException(String message,
                           HttpStatus errorCode) {
        super(message, errorCode);
    }
}
