package ru.nikita.labs.exception.factory;

import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;
import ru.nikita.labs.exception.PolicyException;
import ru.nikita.labs.exception.message.PolicyMessage;

import static ru.nikita.labs.exception.message.PolicyMessage.*;

@UtilityClass
public class PolicyExceptionFactory {

    public static PolicyException roleNotFound() {
        return new PolicyException(
                ROLE_NOT_FOUND,
                HttpStatus.NOT_FOUND);
    }

    public static PolicyException permissionNotFound() {
        return new PolicyException(
                PERMISSION_NOT_FOUND,
                HttpStatus.NOT_FOUND);
    }

    public static PolicyException userRoleNotFound() {
        return new PolicyException(
                USER_ROLE_NOT_FOUND,
                HttpStatus.NOT_FOUND);
    }

    public static PolicyException codeAlreadyExists() {
        return new PolicyException(
                CODE_ALREADY_EXISTS,
                HttpStatus.CONFLICT);
    }

    public static PolicyException forbidden(String permission) {
        final String formattedForbidden = FORBIDDEN.formatted(permission);
        return new PolicyException(
                formattedForbidden,
                HttpStatus.FORBIDDEN
        );
    }

    public static PolicyException forbidden() {
        return new PolicyException(
                USER_FORBIDDEN,
                HttpStatus.FORBIDDEN
        );
    }
}
