package ru.nikita.labs.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Permission {
    USER_LIST("read-list-user", "glu"),
    ROLE_LIST("read-list-role", "glr"),
    PERMISSION_LIST("read-list-permission", "glp"),
    USER_READ("read-user", "ru"),
    ROLE_READ("read-role","rr"),
    PERMISSION_READ("read-permission","rp"),
    USER_CREATE("create-user","cu"),
    ROLE_CREATE("create-role","cr"),
    PERMISSION_CREATE("create-permission","cp"),
    USER_UPDATE("update-user", "uu"),
    ROLE_UPDATE("update-role","ur"),
    PERMISSION_UPDATE("update-permission","up"),
    USER_DELETE("delete-user", "du"),
    ROLE_DELETE("delete-role","dr"),
    PERMISSION_DELETE("delete-permission","dp"),
    ROLE_RESTORE("restore-role","rtr"),
    PERMISSION_RESTORE("restore-permission","rtp");

    private final String name;
    private final String code;
}
