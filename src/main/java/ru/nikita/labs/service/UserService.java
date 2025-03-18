package ru.nikita.labs.service;

import org.springframework.stereotype.Service;
import ru.nikita.labs.dto.UserDto;
import ru.nikita.labs.dto.mapper.UserMapper;
import ru.nikita.labs.dto.request.RegisterRequest;
import ru.nikita.labs.model.User;
import ru.nikita.labs.repository.UserRepository;

import static ru.nikita.labs.exception.factory.AuthExceptionFactory.emailAlreadyExists;
import static ru.nikita.labs.exception.factory.AuthExceptionFactory.userAlreadyExists;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserDto create(RegisterRequest regRequest) {
        checkUsername(regRequest.getUsername());
        checkEmail(regRequest.getEmail());
        User user = UserMapper.toUser(regRequest);
        userRepository.save(user);
        return UserMapper.toUserDto(user);
    }

    private void checkUsername(String username) {
        if (userRepository.existsByUsernameIgnoreCase(username)) {
            throw userAlreadyExists();
        }
    }

    private void checkEmail(String email) {
        if (userRepository.existsByEmailIgnoreCase(email)) {
            throw emailAlreadyExists();
        }
    }

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

}
