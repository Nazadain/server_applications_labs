package ru.nikita.labs.validation.user;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Email {

    public static boolean isNull(String email) {
        return email == null;
    }

    public static boolean isValid(String email) {
        return email.matches(
                "^[\\w-\\.]+@[\\w-]+(\\.[\\w-]+)*\\.[a-z]{2,}$");
    }
}
