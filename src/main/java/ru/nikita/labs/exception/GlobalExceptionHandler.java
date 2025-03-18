package ru.nikita.labs.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.nikita.labs.dto.response.ErrorResponse;

import java.util.HashMap;
import java.util.Map;

import static ru.nikita.labs.exception.message.ValidationMessage.C_PASSWORD_MUST_BE_EQUALS_PASSWORD;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ErrorResponse> handle(AuthException e) {
        return ResponseEntity
                .status(e.getErrorCode())
                .body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(PolicyException.class)
    public ResponseEntity<ErrorResponse> handle(PolicyException e) {
        return ResponseEntity
                .status(e.getErrorCode())
                .body(new ErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handle(
            MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String errorMessage = error.getDefaultMessage();
            if (isCPasswordMessage(errorMessage)) {
                errors.put("c_password", errorMessage);
            } else {
                String fieldName = ((FieldError) error).getField();
                errors.put(fieldName, errorMessage);
            }
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    private boolean isCPasswordMessage(String message) {
        return message != null
                && message.equals(C_PASSWORD_MUST_BE_EQUALS_PASSWORD);
    }
}
