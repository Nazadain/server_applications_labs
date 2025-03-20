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
import ru.nikita.labs.dto.request.UpdatePasswordRequest;
import ru.nikita.labs.dto.response.JwtResponse;
import ru.nikita.labs.exception.AuthException;
import ru.nikita.labs.security.Permission;
import ru.nikita.labs.security.RequiresPermission;
import ru.nikita.labs.service.AuthService;

import static ru.nikita.labs.security.Permission.USER_READ;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

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
        return ResponseEntity.ok()
                .body(jwt);
    }

    @GetMapping("/me")
    @RequiresPermission({USER_READ})
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

    @PatchMapping("/{id}/updatePassword")
    public ResponseEntity<UserDto> updatePassword(
            HttpServletRequest req,
            @RequestBody @Validated
            UpdatePasswordRequest updatePasswordRequest,
            @PathVariable("id") Long id) {
        UserDto userDto = authService.updatePassword(
                req, updatePasswordRequest, id);
        return ResponseEntity.ok().body(userDto);
    }

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
}
