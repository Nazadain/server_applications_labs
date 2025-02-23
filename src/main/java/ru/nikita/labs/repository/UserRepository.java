package ru.nikita.labs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nikita.labs.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    boolean existsByUsernameIgnoreCase(String username);

    boolean existsByEmail(String email);
}
