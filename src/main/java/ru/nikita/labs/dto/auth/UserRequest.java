package ru.nikita.labs.dto.auth;

public interface UserRequest extends Request {
    String getUsername();
    String getPassword();
}
