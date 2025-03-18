package ru.nikita.labs.exception.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PolicyMessage {
    ROLE_NOT_FOUND("Роль не найдена!"),
    PERMISSION_NOT_FOUND("Разрешение на найдено!"),
    NAME_ALREADY_EXISTS("Такое имя уже существует!"),
    CODE_ALREADY_EXISTS("Такой код уже существует!");

    private final String message;
}
