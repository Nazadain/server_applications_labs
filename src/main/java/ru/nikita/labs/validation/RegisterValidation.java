package ru.nikita.labs.validation;

import org.springframework.stereotype.Component;
import ru.nikita.labs.dto.auth.RegisterRequest;
import ru.nikita.labs.exception.AuthException;
import ru.nikita.labs.validation.user.UserValidation;

@Component
public class RegisterValidation extends UserValidation<RegisterRequest> {

    @Override
    public void validate(RegisterRequest body) throws AuthException {
        validateUsername(body.getUsername());
        validatePassword(body.getPassword());
        validateCPassword(body.getCPassword(), body.getPassword());
        validateEmail(body.getEmail());
        validateBirthday(body.getBirthday());
    }
}
