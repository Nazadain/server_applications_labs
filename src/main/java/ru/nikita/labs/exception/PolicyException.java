package ru.nikita.labs.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import ru.nikita.labs.exception.message.PolicyMessage;

@Getter
public class PolicyException extends RuntimeException {
    HttpStatus errorCode;

    public PolicyException(PolicyMessage policyMessage,
                           HttpStatus errorCode) {
        super(policyMessage.getMessage());
        this.errorCode = errorCode;
    }
}
