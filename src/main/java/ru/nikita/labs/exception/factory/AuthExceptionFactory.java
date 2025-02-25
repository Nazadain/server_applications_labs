package ru.nikita.labs.exception.factory;

import org.springframework.http.HttpStatus;
import ru.nikita.labs.exception.AuthException;
import ru.nikita.labs.exception.message.AuthMessage;

public class AuthExceptionFactory {

    public static AuthException userAlreadyExists() {
        return new AuthException(
                AuthMessage.USER_ALREADY_EXISTS,
                HttpStatus.CONFLICT);
    }

    public static AuthException badCredentials() {
        return new AuthException(
                AuthMessage.WRONG_USERNAME_OR_PASSWORD,
                HttpStatus.BAD_REQUEST);
    }

    public static AuthException unauthorized() {
        return new AuthException(
                AuthMessage.NOT_AUTHORIZED,
                HttpStatus.UNAUTHORIZED);
    }

    public static AuthException wrongPassword() {
        return new AuthException(
                AuthMessage.WRONG_PASSWORD,
                HttpStatus.BAD_REQUEST);
    }
}
