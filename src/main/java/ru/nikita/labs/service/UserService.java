package ru.nikita.labs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.nikita.labs.dto.request.UpdatePasswordRequest;
import ru.nikita.labs.dto.UserDto;
import ru.nikita.labs.exception.AuthException;
import ru.nikita.labs.exception.message.AuthMessage;
import ru.nikita.labs.model.User;
import ru.nikita.labs.repository.UserRepository;
import ru.nikita.labs.util.Crypto;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserDto updatePassword(
            UpdatePasswordRequest request, Long id) {
        User user = userRepository.getReferenceById(id);
        String salt = user.getSalt();

        String encodedOldPassword = Crypto.sha256Hex(
                request.getOldPassword(), salt);
        if (!encodedOldPassword.equals(user.getPassword())) {
            throw new AuthException(
                    AuthMessage.WRONG_PASSWORD,
                    HttpStatus.NOT_ACCEPTABLE);
        }
        String encodedNewPassword = Crypto.sha256Hex(
                request.getNewPassword(), salt);
        user.setPassword(encodedNewPassword);
        userRepository.save(user);
        return UserDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .birthday(user.getBirthday())
                .build();
    }
}
