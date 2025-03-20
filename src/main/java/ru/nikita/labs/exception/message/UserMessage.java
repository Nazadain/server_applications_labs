package ru.nikita.labs.exception.message;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserMessage {
    NOT_FOUND("Пользователь не найден!");

    private final String message;
}
