package ru.nikita.labs.validation.user;

import lombok.experimental.UtilityClass;
import org.springframework.http.HttpStatus;
import ru.nikita.labs.exception.ValidationException;
import ru.nikita.labs.exception.message.ValidationMessage;

import java.time.LocalDate;

@UtilityClass
public class User {
    public static void validateUsername(String username)
            throws ValidationException {
        if (Username.isNull(username)) {
            throw new ValidationException(
                    ValidationMessage.USERNAME_CANNOT_BE_NULL,
                    HttpStatus.BAD_REQUEST);
        }
        if (!Username.isLatin(username)) {
            throw new ValidationException(
                    ValidationMessage.USERNAME_MUST_BE_LATIN,
                    HttpStatus.BAD_REQUEST);
        }
        if (!Username.isFirstLetterUpperCase(username)) {
            throw new ValidationException(
                    ValidationMessage.USERNAME_MUST_START_WITH_UPPER,
                    HttpStatus.BAD_REQUEST);
        }
    }

    public static void validatePassword(String password)
            throws ValidationException {
        if (Password.isNull(password)) {
            throw new ValidationException(
                    ValidationMessage.PASSWORD_CANNOT_BE_NULL,
                    HttpStatus.BAD_REQUEST);
        }
        if (!Password.isLengthCorrect(password, 8)) {
            throw new ValidationException(
                    ValidationMessage.PASSWORD_IS_TOO_SHORT,
                    HttpStatus.BAD_REQUEST);
        }
        if (!Password.isContainsNumber(password)) {
            throw new ValidationException(
                    ValidationMessage.PASSWORD_MUST_CONTAIN_NUMBER,
                    HttpStatus.BAD_REQUEST);
        }

        boolean isContainsLetter = Password.isContainsLetter(password);
        boolean isContainsBothCases = Password.isContainsBothCases(password);

        if (!isContainsLetter || !isContainsBothCases) {
            throw new ValidationException(
                    ValidationMessage.PASSWORD_MUST_CONTAIN_BOTH_CASES,
                    HttpStatus.BAD_REQUEST);
        }
    }

    public static void validateCPassword(String cPassword, String password)
            throws ValidationException {
        if (cPassword == null) {
            throw new ValidationException(
                    ValidationMessage.C_PASSWORD_CANNOT_BE_NULL,
                    HttpStatus.BAD_REQUEST);
        }
        if (!cPassword.equals(password)) {
            throw new ValidationException(
                    ValidationMessage.C_PASSWORD_EQUALS_PASSWORD,
                    HttpStatus.BAD_REQUEST);
        }
    }

    public static void validateEmail(String email)
            throws ValidationException {
        if (Email.isNull(email)) {
            throw new ValidationException(
                    ValidationMessage.EMAIL_CANNOT_BE_NULL,
                    HttpStatus.BAD_REQUEST);
        }
        if (!Email.isValid(email)) {
            throw new ValidationException(
                    ValidationMessage.WRONG_EMAIL_FORMAT,
                    HttpStatus.BAD_REQUEST);
        }
    }

    public static void validateBirthday(LocalDate birthday)
            throws ValidationException {
        if (Birthday.isNull(birthday)) {
            throw new ValidationException(
                    ValidationMessage.BIRTHDAY_CANNOT_BE_NULL,
                    HttpStatus.BAD_REQUEST);
        }
        if (!Birthday.isEqualOrOverYears(birthday, 14)) {
            throw new ValidationException(
                    ValidationMessage.BIRTHDAY_MUST_BE_GOE_FOURTEEN,
                    HttpStatus.BAD_REQUEST);
        }
    }

}
