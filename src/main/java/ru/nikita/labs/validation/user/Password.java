package ru.nikita.labs.validation.user;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Password {

    public static boolean isNull(String password) {
        return password == null;
    }

    public static boolean isLengthCorrect(String password, int length) {
        return password.length() >= length;
    }

    public static boolean isContainsLetter(String password) {
        return password.matches(".*\\D.*");
    }

    public static boolean isContainsNumber(String password) {
        return password.matches(".*\\d.*");
    }

    public static boolean isContainsBothCases(String password) {
        return password.matches(".*[A-Z].*")
                && password.matches(".*[a-z].*");
    }
}
