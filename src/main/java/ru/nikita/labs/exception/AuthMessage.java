package ru.nikita.labs.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthMessage {
    NOT_AUTHORIZED("Пользователь не авторизован!"),

    USER_ALREADY_EXISTS(
            "Пользователь с таким именем уже существует!"),

    EMAIL_ALREADY_EXISTS("Этот email уже используется!"),

    USERNAME_CANNOT_BE_NULL("Имя пользователя не может быть пустым!"),

    USERNAME_IS_TOO_SHORT(
            "Имя пользователя должно содержать не менее 7 символов!"),

    USERNAME_MUST_BE_LATIN(
            "Имя пользователя должно содержать только буквы латинского алфавита!"),

    USERNAME_MUST_START_WITH_UPPER(
            "Имя пользователя должно начинаться с большой буквы!"),

    PASSWORD_CANNOT_BE_NULL("Пароль не может быть пустым!"),

    PASSWORD_IS_TOO_SHORT("Пароль должен содержать не менее 8 символов!"),

    PASSWORD_MUST_CONTAIN_NUMBER("Пароль должен содержать цифры!"),

    PASSWORD_MUST_CONTAIN_BOTH_CASES(
            "Пароль должен содержать буквы в верхнем и нижнем регистре!"),

    C_PASSWORD_CANNOT_BE_NULL("Введите подтверждение пароля!"),

    C_PASSWORD_EQUALS_PASSWORD("Пароли должны совпадать!"),

    EMAIL_CANNOT_BE_NULL("Email не может быть пустым!"),

    WRONG_EMAIL_FORMAT("Email должен иметь вид: ***@***.***"),

    BIRTHDAY_CANNOT_BE_NULL("Дата рождения не может быть пустой!"),

    BIRTHDAY_MUST_BE_GOE_FOURTEEN(
            "Пользователю должно быть не меньше 14 лет!");

    private final String message;
}
