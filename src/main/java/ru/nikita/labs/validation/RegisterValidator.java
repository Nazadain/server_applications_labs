package ru.nikita.labs.validation;

import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.nikita.labs.dto.auth.RegisterRequest;
import ru.nikita.labs.exception.ValidationException;

import static ru.nikita.labs.validation.user.User.*;

@Getter
@Component
public class RegisterValidator implements Validator<RegisterRequest> {

    @Override
    public void validate(RegisterRequest body) throws ValidationException {
        validateUsername(body.getUsername());
        validatePassword(body.getPassword());
        validateCPassword(body.getCPassword(), body.getPassword());
        validateEmail(body.getEmail());
        validateBirthday(body.getBirthday());
    }
}
