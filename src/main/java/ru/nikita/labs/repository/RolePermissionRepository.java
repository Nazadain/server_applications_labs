package ru.nikita.labs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nikita.labs.model.Permission;
import ru.nikita.labs.model.Role;
import ru.nikita.labs.model.RolePermission;

import java.util.Optional;

public interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {
    Optional<RolePermission> findByRole(Role role);

    Optional<RolePermission> findByPermission(Permission permission);
}
