package ru.nikita.labs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nikita.labs.dto.auth.*;
import ru.nikita.labs.exception.AuthException;
import ru.nikita.labs.model.User;
import ru.nikita.labs.service.AuthService;
import ru.nikita.labs.validation.RegisterValidation;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private RegisterValidation registerValidation;

    @PostMapping("/register")
    public ResponseEntity<Response> register(@RequestBody RegisterRequest user) {
        try {
            registerValidation.validate(user);
            UserResponse newUser = authService.register(user);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(newUser);
        } catch (AuthException e) {
            return ResponseEntity.status(e.getErrorCode())
                    .body(new ErrorResponse(e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody LoginRequest user) {
        System.out.println(user);
        return ResponseEntity.ok(new UserResponse());
    }

    @GetMapping("/me")
    public ResponseEntity<Response> me() {
        return ResponseEntity.ok(new UserResponse());
    }

    @PostMapping("/out")
    public ResponseEntity<Response> out(@RequestBody User user) {
        return ResponseEntity.ok(new UserResponse());
    }

    @GetMapping("/tokens")
    public ResponseEntity<Response> tokens() {
        return ResponseEntity.ok(new UserResponse());
    }

    @PostMapping("out_all")
    public ResponseEntity<Response> allOut(@RequestBody User user) {
        return ResponseEntity.ok(new UserResponse());
    }
}
