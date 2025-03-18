package ru.nikita.labs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nikita.labs.model.Role;
import ru.nikita.labs.model.User;
import ru.nikita.labs.model.UserRole;

import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    Optional<UserRole> findByUser(User user);
    Optional<UserRole> findByRole(Role role);
}
