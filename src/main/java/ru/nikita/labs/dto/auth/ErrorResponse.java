package ru.nikita.labs.dto.auth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.nikita.labs.exception.AuthMessage;

@Getter
@NoArgsConstructor
public class ErrorResponse implements Response {
    private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }
}
