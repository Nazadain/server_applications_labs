package ru.nikita.labs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nikita.labs.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsernameAndPassword(String username,
                                         String password);
    boolean existsByUsernameIgnoreCase(String username);
    boolean existsByEmail(String email);
}
