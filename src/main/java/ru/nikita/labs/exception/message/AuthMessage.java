package ru.nikita.labs.exception.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AuthMessage {
    NOT_AUTHORIZED("Пользователь не авторизован!"),

    CANNOT_BE_AUTHORIZED(
            "Этот маршрут доступен только не авторизованным пользователям!"),

    WRONG_USERNAME_OR_PASSWORD(
            "Неправильное имя пользователя или пароль!"),

    USER_ALREADY_EXISTS(
            "Пользователь с таким именем уже существует!"),

    EMAIL_ALREADY_EXISTS("Этот email уже используется!"),

    WRONG_PASSWORD("Вы ввели неправильный пароль!");

    private final String message;
}