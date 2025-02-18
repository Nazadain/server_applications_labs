package ru.nikita.labs.validation.user;

import org.springframework.http.HttpStatus;
import ru.nikita.labs.dto.auth.UserRequest;
import ru.nikita.labs.exception.AuthException;
import ru.nikita.labs.exception.AuthMessage;
import ru.nikita.labs.validation.Validation;

import java.time.LocalDate;

public abstract class UserValidation<T extends UserRequest> implements Validation<T> {

    protected void validateUsername(String username) throws AuthException {
        if (Username.isNull(username)) {
            throw new AuthException(
                    AuthMessage.USERNAME_CANNOT_BE_NULL,
                    HttpStatus.BAD_REQUEST);
        }
        if (!Username.isLatin(username)) {
            throw new AuthException(
                    AuthMessage.USERNAME_MUST_BE_LATIN,
                    HttpStatus.BAD_REQUEST);
        }
        if (!Username.isFirstLetterUpperCase(username)) {
            throw new AuthException(
                    AuthMessage.USERNAME_MUST_START_WITH_UPPER,
                    HttpStatus.BAD_REQUEST);
        }
    }

    protected void validatePassword(String password) throws AuthException {
        if (Password.isNull(password)) {
            throw new AuthException(
                    AuthMessage.PASSWORD_CANNOT_BE_NULL,
                    HttpStatus.BAD_REQUEST);
        }
        if (!Password.isLengthCorrect(password, 8)) {
            throw new AuthException(
                    AuthMessage.PASSWORD_IS_TOO_SHORT,
                    HttpStatus.BAD_REQUEST);
        }
        if (!Password.isContainsNumber(password)) {
            throw new AuthException(
                    AuthMessage.PASSWORD_MUST_CONTAIN_NUMBER,
                    HttpStatus.BAD_REQUEST);
        }

        boolean isContainsLetter = Password.isContainsLetter(password);
        boolean isContainsBothCases = Password.isContainsBothCases(password);

        if (!isContainsLetter || !isContainsBothCases) {
            throw new AuthException(
                    AuthMessage.PASSWORD_MUST_CONTAIN_BOTH_CASES,
                    HttpStatus.BAD_REQUEST);
        }
    }

    protected void validateCPassword(String cPassword, String password) throws AuthException {
        if (cPassword == null) {
            throw new AuthException(
                    AuthMessage.C_PASSWORD_CANNOT_BE_NULL,
                    HttpStatus.BAD_REQUEST);
        }
        if (!cPassword.equals(password)) {
            throw new AuthException(
                    AuthMessage.C_PASSWORD_EQUALS_PASSWORD,
                    HttpStatus.BAD_REQUEST);
        }
    }

    protected void validateEmail(String email) throws AuthException {
        if (Email.isNull(email)) {
            throw new AuthException(
                    AuthMessage.EMAIL_CANNOT_BE_NULL,
                    HttpStatus.BAD_REQUEST);
        }
        if (!Email.isValid(email)) {
            throw new AuthException(
                    AuthMessage.WRONG_EMAIL_FORMAT,
                    HttpStatus.BAD_REQUEST);
        }
    }

    protected void validateBirthday(LocalDate birthday) throws AuthException {
        if (Birthday.isNull(birthday)) {
            throw new AuthException(
                    AuthMessage.BIRTHDAY_CANNOT_BE_NULL,
                    HttpStatus.BAD_REQUEST);
        }
        if (!Birthday.isEqualOrOverYears(birthday, 14)) {
            throw new AuthException(
                    AuthMessage.BIRTHDAY_MUST_BE_GOE_FOURTEEN,
                    HttpStatus.BAD_REQUEST);
        }
    }

}
