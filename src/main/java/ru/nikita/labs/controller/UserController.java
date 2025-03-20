package ru.nikita.labs.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nikita.labs.dto.RoleDto;
import ru.nikita.labs.dto.UserDto;
import ru.nikita.labs.security.RequiresPermission;
import ru.nikita.labs.service.UserService;

import java.util.List;

import static ru.nikita.labs.security.Permission.*;

@RestController
@RequestMapping("/api/ref/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/")
    @RequiresPermission({USER_LIST})
    public ResponseEntity<List<UserDto>> findAll() {
        List<UserDto> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}/role")
    @RequiresPermission({ROLE_LIST})
    public ResponseEntity<List<RoleDto>> getRoles(
            @PathVariable("id") Long id) {
        List<RoleDto> roles = userService.getRoles(id);
        return ResponseEntity.ok(roles);
    }

    @PostMapping("/{id}/role")
    @RequiresPermission({ROLE_CREATE})
    public ResponseEntity<Void> setRole(
            HttpServletRequest req,
            @PathVariable("id") Long id,
            @RequestBody String code) {
        userService.setRole(req, id, code);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/role/{code}")
    @RequiresPermission({ROLE_DELETE})
    public ResponseEntity<Void> deleteRole(
            @PathVariable("id") Long id,
            @PathVariable("code") String code) {
        userService.deleteRole(id, code);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/role/{code}/soft")
    @RequiresPermission({ROLE_DELETE})
    public ResponseEntity<Void> softDeleteRole(
            HttpServletRequest req,
            @PathVariable("id") Long id,
            @PathVariable("code") String code) {
        userService.softDeleteRole(req, id, code);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/role/{code}/restore")
    @RequiresPermission({ROLE_RESTORE})
    public ResponseEntity<Void> restoreRole(
            @PathVariable("id") Long id,
            @PathVariable("code") String code) {
        userService.restoreRole(id, code);
        return ResponseEntity.ok().build();
    }

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
}
