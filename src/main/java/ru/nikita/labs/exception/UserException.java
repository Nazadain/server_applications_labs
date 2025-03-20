package ru.nikita.labs.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import ru.nikita.labs.exception.message.UserMessage;

@Getter
public class UserException extends CrudException {
    public UserException(UserMessage userMessage,
                         HttpStatus errorCode) {
        super(userMessage.getMessage(), errorCode);
    }
}
