package ru.nikita.labs.validation;

import org.springframework.stereotype.Component;
import ru.nikita.labs.dto.auth.LoginRequest;
import ru.nikita.labs.exception.ValidationException;

import static ru.nikita.labs.validation.user.User.validatePassword;
import static ru.nikita.labs.validation.user.User.validateUsername;

@Component
public class LoginValidator implements Validator<LoginRequest> {

    @Override
    public void validate(LoginRequest body) throws ValidationException {
        validateUsername(body.getUsername());
        validatePassword(body.getPassword());
    }

}
