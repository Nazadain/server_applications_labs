package ru.nikita.labs.validation;

import org.springframework.stereotype.Component;
import ru.nikita.labs.dto.auth.LoginRequest;
import ru.nikita.labs.exception.AuthException;
import ru.nikita.labs.validation.user.UserValidation;

@Component
public class LoginValidation extends UserValidation<LoginRequest> {

    @Override
    public void validate(LoginRequest body) throws AuthException {
        validateUsername(body.getUsername());
        validatePassword(body.getPassword());
    }

}
