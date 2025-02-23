package ru.nikita.labs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.nikita.labs.dto.request.UpdatePasswordRequest;
import ru.nikita.labs.dto.UserDto;
import ru.nikita.labs.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PutMapping("/{id}/updatePassword")
    public ResponseEntity<UserDto> updatePassword(
            @RequestBody @Validated
            UpdatePasswordRequest updatePasswordRequest,
            @PathVariable("id") Long id) {
        UserDto user = userService.updatePassword(
                updatePasswordRequest, id);
        return ResponseEntity.ok().body(user);
    }
}
