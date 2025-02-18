package ru.nikita.labs.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    ADMIN("Админ", "Admin"),
    USER("Пользователь", "User");

    private final String ruValue;
    private final String enValue;
}
