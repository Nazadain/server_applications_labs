package ru.nikita.labs.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nikita.labs.dto.auth.JwtResponse;
import ru.nikita.labs.dto.auth.LoginRequest;
import ru.nikita.labs.dto.auth.RegisterRequest;
import ru.nikita.labs.dto.auth.UserDto;
import ru.nikita.labs.exception.AuthException;
import ru.nikita.labs.exception.ValidationException;
import ru.nikita.labs.exception.message.AuthMessage;
import ru.nikita.labs.model.User;
import ru.nikita.labs.service.AuthService;
import ru.nikita.labs.service.JwtService;
import ru.nikita.labs.validation.LoginValidator;
import ru.nikita.labs.validation.RegisterValidator;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(
            @RequestBody RegisterRequest user,
            HttpServletRequest req)
            throws AuthException, ValidationException {
        RegisterValidator validator = new RegisterValidator();
        validator.validate(user);
        UserDto newUser = authService.register(user, req);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(newUser);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest user,
                                             HttpServletResponse resp)
            throws AuthException, ValidationException {
        LoginValidator validator = new LoginValidator();
        validator.validate(user);
        JwtResponse token = authService.login(user, resp);
        return ResponseEntity.status(HttpStatus.OK)
                .body(token);
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> me(HttpServletRequest req) {
        UserDto user = authService.me(req);
        return ResponseEntity.status(HttpStatus.OK)
                .body(user);
    }

    @PostMapping("/out")
    public void out(HttpServletRequest req, HttpServletResponse resp) {
        authService.out(req, resp);
    }

    @GetMapping("/tokens")
    public ResponseEntity<UserDto> tokens() {
        return ResponseEntity.ok(new UserDto());
    }

    @PostMapping("out_all")
    public ResponseEntity<UserDto> allOut(@RequestBody User user) {
        return ResponseEntity.ok(new UserDto());
    }
}
