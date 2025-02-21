package ru.nikita.labs.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.nikita.labs.dto.auth.ErrorResponse;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handle(ValidationException e) {
        return ResponseEntity
                .status(e.getErrorCode())
                .body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ErrorResponse> handle(AuthException e) {
        return ResponseEntity
                .status(e.getErrorCode())
                .body(new ErrorResponse(e.getMessage()));
    }
}
