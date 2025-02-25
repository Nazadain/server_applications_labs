package ru.nikita.labs.service;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nikita.labs.dto.UserDto;
import ru.nikita.labs.dto.mapper.UserMapper;
import ru.nikita.labs.dto.request.RegisterRequest;
import ru.nikita.labs.dto.request.UpdatePasswordRequest;
import ru.nikita.labs.exception.AuthException;
import ru.nikita.labs.model.User;
import ru.nikita.labs.repository.UserRepository;
import ru.nikita.labs.util.Crypto;

import static ru.nikita.labs.exception.factory.AuthExceptionFactory.userAlreadyExists;
import static ru.nikita.labs.exception.factory.AuthExceptionFactory.wrongPassword;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public UserDto create(@Valid RegisterRequest userData)
            throws AuthException {
        if (userRepository.existsByUsernameIgnoreCase(
                userData.getUsername())) {
            throw userAlreadyExists();
        }
        User newUser = userMapper.getUserFromRegisterRequest(userData);
        userRepository.save(newUser);
        return userMapper.getUserDtoFromUser(newUser);
    }

    public UserDto updatePassword(
            @Valid UpdatePasswordRequest updatePasswordReq,
            Long id) {
        User user = userRepository.getReferenceById(id);
        String salt = user.getSalt();

        String encodedOldPassword = Crypto.sha256Hex(
                updatePasswordReq.getOldPassword(), salt);
        if (!encodedOldPassword.equals(user.getPassword())) {
            throw wrongPassword();
        }
        String encodedNewPassword = Crypto.sha256Hex(
                updatePasswordReq.getNewPassword(), salt);
        user.setPassword(encodedNewPassword);
        userRepository.save(user);
        return userMapper.getUserDtoFromUser(user);
    }

    @Autowired
    public UserService(UserRepository userRepository,
                       UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }
}
