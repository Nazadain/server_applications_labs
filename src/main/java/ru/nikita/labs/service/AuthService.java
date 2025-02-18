package ru.nikita.labs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.nikita.labs.dto.auth.LoginRequest;
import ru.nikita.labs.dto.auth.RegisterRequest;
import ru.nikita.labs.dto.auth.UserResponse;
import ru.nikita.labs.exception.AuthException;
import ru.nikita.labs.exception.AuthMessage;
import ru.nikita.labs.model.User;
import ru.nikita.labs.repository.UserRepository;

import static ru.nikita.labs.util.Crypto.getSalt;
import static ru.nikita.labs.util.Crypto.sha256Hex;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public UserResponse register(RegisterRequest userReq) throws AuthException {
        checkUsername(userReq.getUsername());
        checkEmail(userReq.getEmail());

        String encodedPassword = sha256Hex(
                userReq.getPassword(),
                getSalt());
        userReq.setPassword(encodedPassword);

        User newUser = buildUser(userReq);
        userRepository.save(newUser);

        return UserResponse.builder()
                .username(newUser.getUsername())
                .email(newUser.getEmail())
                .birthday(newUser.getBirthday())
                .role(newUser.getRole())
                .build();
    }

    public UserResponse login(LoginRequest userReq) throws AuthException {
        return new UserResponse();
    }

    private User buildUser(RegisterRequest userReq) {
        return User.builder()
                .username(userReq.getUsername())
                .password(userReq.getPassword())
                .email(userReq.getEmail())
                .birthday(userReq.getBirthday())
                .role(userReq.getRole())
                .build();
    }

    private void checkUsername(String username) throws AuthException {
        if (userRepository.existsByUsernameIgnoreCase(username)) {
            throw new AuthException(
                    AuthMessage.USER_ALREADY_EXISTS,
                    HttpStatus.CONFLICT);
        }
    }

    private void checkEmail(String email) throws AuthException {
        if (userRepository.existsByEmail(email)) {
            throw new AuthException(
                    AuthMessage.EMAIL_ALREADY_EXISTS,
                    HttpStatus.CONFLICT);
        }
    }
}
