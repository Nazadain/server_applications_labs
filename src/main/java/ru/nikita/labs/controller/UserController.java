package ru.nikita.labs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.nikita.labs.dto.UserDto;
import ru.nikita.labs.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/ref/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
}
