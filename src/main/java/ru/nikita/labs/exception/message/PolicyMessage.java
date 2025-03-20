package ru.nikita.labs.exception.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.UtilityClass;


@UtilityClass
public class PolicyMessage {
    public static final String ROLE_NOT_FOUND =
            "Роль не найдена!";
    public static final String PERMISSION_NOT_FOUND =
            "Разрешение не найдено!";
    public static final String USER_ROLE_NOT_FOUND =
            "Роль пользователя не найдена!";
    public static final String CODE_ALREADY_EXISTS =
            "Такой код уже существует!";
    public static final String FORBIDDEN =
            "Доступ к ресурсу запрещен! Требуется разрешение %s";
    public static final String USER_FORBIDDEN =
            "Доступ к данным другого пользователя запрещен!";
}
