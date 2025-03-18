package ru.nikita.labs.exception.factory;

import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;
import ru.nikita.labs.exception.PolicyException;

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

    public static PolicyException codeAlreadyExists() {
        return new PolicyException(
                CODE_ALREADY_EXISTS,
                HttpStatus.CONFLICT);
    }

}
