package ru.nikita.labs.exception.message;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ValidationMessage {
    public static final String USERNAME_CANNOT_BE_NULL =
            "Имя пользователя не может быть пустым!";
    public static final String WRONG_USERNAME_SIZE =
            "Имя пользователя должно быть от 7 до 30 символов!";
    public static final String WRONG_USERNAME_PATTERN =
            "Имя пользователя должно начинаться с большой буквы и " +
                    "состоять только из букв латинского алфавита!";
    public static final String PASSWORD_CANNOT_BE_NULL =
            "Пароль не может быть пустым!";
    public static final String WRONG_PASSWORD_SIZE =
            "Пароль должен быть от 8 до 30 символов!";
    public static final String WRONG_PASSWORD_PATTERN =
            "Пароль должен содержать цифры и буквы в верхнем и нижнем регистрах!";
    public static final String C_PASSWORD_MUST_BE_EQUALS_PASSWORD =
            "Пароли должны совпадать!";
    public static final String EMAIL_CANNOT_BE_NULL =
            "Email не может быть пустым!";
    public static final String WRONG_EMAIL_FORMAT =
            "Неправильный формат email!";
    public static final String BIRTHDAY_CANNOT_BE_NULL =
            "Дата рождения не может быть пустой!";
    public static final String BIRTHDAY_MUST_BE_GOE_FOURTEEN =
            "Пользователю должно быть не меньше 14 лет!";
    public static final String NAME_CANNOT_BE_NULL =
            "Поле с именем не может быть пустым!";
    public static final String CODE_CANNOT_BE_NULL =
            "Поле с кодом не может быть пустым!";
    public static final String CREATED_BY_CANNOT_BE_NULL =
            "Поле created_by не может быть пустым!";
}
