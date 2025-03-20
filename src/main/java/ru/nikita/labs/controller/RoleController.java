package ru.nikita.labs.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nikita.labs.dto.RoleDto;
import ru.nikita.labs.dto.request.RoleRequest;
import ru.nikita.labs.security.RequiresPermission;
import ru.nikita.labs.service.RoleService;

import java.util.List;

import static ru.nikita.labs.security.Permission.*;

@RestController
@RequestMapping("/api/ref/policy/role")
public class RoleController {
    private final RoleService roleService;

    @GetMapping("/")
    @RequiresPermission({ROLE_LIST})
    public ResponseEntity<List<RoleDto>> findAll() {
        List<RoleDto> roleDtos = roleService.findAll();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(roleDtos);
    }

    @GetMapping("/{code}")
    @RequiresPermission({ROLE_READ})
    public RoleDto findByCode(@PathVariable("code") String code) {
        return roleService.findByCode(code);
    }

    @PostMapping("/")
    @RequiresPermission({ROLE_CREATE})
    public ResponseEntity<RoleDto> save(HttpServletRequest req,
                                        @RequestBody RoleRequest role) {
        RoleDto roleDto = roleService.save(req, role);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(roleDto);
    }

    @PutMapping("/{code}")
    @RequiresPermission({ROLE_UPDATE})
    public ResponseEntity<RoleDto> update(@RequestBody RoleRequest roleRequest,
                                          @PathVariable("code") String code) {
        RoleDto roleDto = roleService.update(roleRequest, code);
        return ResponseEntity.ok(roleDto);
    }

    @DeleteMapping("/{code}")
    @RequiresPermission({ROLE_DELETE})
    public void delete(@PathVariable("code") String code) {
        roleService.delete(code);
    }

    @DeleteMapping("/{code}/soft")
    @RequiresPermission({ROLE_DELETE})
    public void softDelete(HttpServletRequest req,
                           @PathVariable("code") String code) {
        roleService.softDelete(req, code);
    }

    @PostMapping("/{code}/restore")
    @RequiresPermission({ROLE_RESTORE})
    public void restore(@PathVariable("code") String code) {
        roleService.restore(code);
    }

    @PostMapping("/{roleCode}/permission/{permissionCode}")
    @RequiresPermission({PERMISSION_CREATE})
    public void setPermission(
            HttpServletRequest req,
            @PathVariable("roleCode") String roleCode,
            @PathVariable("permissionCode") String permissionCode) {
        roleService.setPermission(req, roleCode, permissionCode);
    }

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }
}
