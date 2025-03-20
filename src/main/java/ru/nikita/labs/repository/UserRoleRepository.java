package ru.nikita.labs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nikita.labs.model.Role;
import ru.nikita.labs.model.User;
import ru.nikita.labs.model.UserRole;

import java.util.List;
import java.util.Optional;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    List<UserRole> findByUser(User user);

    List<UserRole> findByRole(Role role);

    Optional<UserRole> findByUserAndRole(User user, Role role);

    void deleteByUserAndRole(User user, Role role);
}
