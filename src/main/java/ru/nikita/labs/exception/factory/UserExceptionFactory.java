package ru.nikita.labs.exception.factory;

import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;
import ru.nikita.labs.exception.UserException;

import static ru.nikita.labs.exception.message.UserMessage.NOT_FOUND;

@UtilityClass
public class UserExceptionFactory {

    public static UserException notFound() {
        return new UserException(
                NOT_FOUND,
                HttpStatus.NOT_FOUND);
    }
}
