package ru.nikita.labs.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nikita.labs.dto.PermissionDto;
import ru.nikita.labs.dto.request.PermissionRequest;
import ru.nikita.labs.security.RequiresPermission;
import ru.nikita.labs.service.PermissionService;

import java.util.List;

import static ru.nikita.labs.security.Permission.*;

@RestController
@RequestMapping("/api/ref/policy/permission")
public class PermissionController {
    private final PermissionService permissionService;

    @GetMapping("/")
    @RequiresPermission({PERMISSION_LIST})
    public ResponseEntity<List<PermissionDto>> findAll() {
        List<PermissionDto> permissions = permissionService.findAll();
        return ResponseEntity.ok(permissions);
    }

    @GetMapping("/{code}")
    @RequiresPermission({PERMISSION_READ})
    public ResponseEntity<PermissionDto> findByCode(
            @PathVariable("code") String code) {
        PermissionDto permissionDto = permissionService.findByCode(code);
        return ResponseEntity.ok(permissionDto);
    }

    @PostMapping("/")
    @RequiresPermission({PERMISSION_CREATE})
    public ResponseEntity<PermissionDto> save(
            @RequestBody
            @Valid
            PermissionRequest permissionRequest) {
        PermissionDto permissionDto = permissionService.save(permissionRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(permissionDto);
    }

    @PutMapping("/{code}")
    @RequiresPermission({PERMISSION_UPDATE})
    public ResponseEntity<PermissionDto> update(
            @PathVariable("code") String code,
            @RequestBody
            @Valid
            PermissionRequest permissionRequest) {
        PermissionDto permissionDto = permissionService
                .update(code, permissionRequest);
        return ResponseEntity.ok(permissionDto);
    }

    @DeleteMapping("/{code}")
    @RequiresPermission({PERMISSION_DELETE})
    public ResponseEntity<Void> delete(@PathVariable("code") String code) {
        permissionService.delete(code);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{code}/soft")
    @RequiresPermission({PERMISSION_DELETE})
    public ResponseEntity<Void> softDelete(@PathVariable("code") String code) {
        permissionService.softDelete(code);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{code}/restore")
    @RequiresPermission({PERMISSION_RESTORE})
    public ResponseEntity<Void> restore(@PathVariable("code") String code) {
        permissionService.restore(code);
        return ResponseEntity.ok().build();
    }

    @Autowired
    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }
}
