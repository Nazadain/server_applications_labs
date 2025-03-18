package ru.nikita.labs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nikita.labs.model.Permission;

import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Optional<Permission> findByCode(String code);

    void deleteByCode(String code);
}
