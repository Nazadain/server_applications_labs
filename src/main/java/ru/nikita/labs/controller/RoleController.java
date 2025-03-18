package ru.nikita.labs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nikita.labs.dto.RoleDto;
import ru.nikita.labs.dto.request.RoleRequest;
import ru.nikita.labs.service.RoleService;

import java.util.List;

@RestController
@RequestMapping("/api/ref/policy/role")
public class RoleController {
    private final RoleService roleService;

    @GetMapping("/")
    public ResponseEntity<List<RoleDto>> findAll() {
        List<RoleDto> roleDtos = roleService.findAll();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(roleDtos);
    }

    @GetMapping("/{code}")
    public RoleDto findByCode(@PathVariable("code") String code) {
        return roleService.findByCode(code);
    }

    @PostMapping("/")
    public ResponseEntity<RoleDto> save(@RequestBody RoleRequest role) {
        RoleDto roleDto = roleService.save(role);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(roleDto);
    }

    @PutMapping("/{code}")
    public ResponseEntity<RoleDto> update(@RequestBody RoleRequest roleRequest,
                                          @PathVariable("code") String code) {
        RoleDto roleDto = roleService.update(roleRequest, code);
        return ResponseEntity.ok(roleDto);
    }

    @DeleteMapping("/{code}")
    public void delete(@PathVariable("code") String code) {
        roleService.delete(code);
    }

    @DeleteMapping("/{code}/soft")
    public void softDelete(@PathVariable("code") String code) {
        roleService.softDelete(code);
    }

    @PostMapping("/{code}/restore")
    public void restore(@PathVariable("code") String code) {
        roleService.restore(code);
    }

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }
}
