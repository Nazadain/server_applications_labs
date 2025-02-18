package ru.nikita.labs.validation.user;

import lombok.experimental.UtilityClass;
import ru.nikita.labs.validation.Validation;

@UtilityClass
public class Username {

    public static boolean isNull(String username) {
        return username == null;
    }

    public static boolean isLatin(String username) {
        return username.matches("[a-zA-Z]+");
    }

    public static boolean isFirstLetterUpperCase(String username) {
        return username.matches("[A-Z]+[a-zA-Z]*");
    }
}
