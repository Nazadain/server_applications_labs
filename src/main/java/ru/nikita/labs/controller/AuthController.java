package ru.nikita.labs.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.nikita.labs.dto.UserDto;
import ru.nikita.labs.dto.request.LoginRequest;
import ru.nikita.labs.dto.request.RegisterRequest;
import ru.nikita.labs.dto.response.JwtResponse;
import ru.nikita.labs.exception.AuthException;
import ru.nikita.labs.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(
            @RequestBody @Validated RegisterRequest registerRequest) {
        UserDto user = authService.register(registerRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(user);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(
            @RequestBody @Validated LoginRequest loginRequest,
            HttpServletResponse resp) {
        JwtResponse jwt = authService.login(loginRequest, resp);
        return ResponseEntity.status(HttpStatus.OK)
                .body(jwt);
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> me(HttpServletRequest req)
            throws AuthException {
        UserDto user = authService.me(req);
        return ResponseEntity.ok()
                .body(user);
    }

    @PostMapping("/out")
    public void logout(HttpServletResponse resp) {
        authService.logout(resp);
    }

    @GetMapping("/refresh")
    public ResponseEntity<JwtResponse> refresh(
            HttpServletRequest req,
            HttpServletResponse resp) {
        String newAccessToken = authService.refresh(req, resp);
        return ResponseEntity.ok()
                .body(new JwtResponse(newAccessToken));
    }
}
