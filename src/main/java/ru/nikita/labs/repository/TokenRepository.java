package ru.nikita.labs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.nikita.labs.model.Token;
import ru.nikita.labs.model.User;

import java.util.List;

public interface TokenRepository extends JpaRepository<Token, Long> {
    Token findByValue(String value);
    List<Token> findByUser(User user);
}
