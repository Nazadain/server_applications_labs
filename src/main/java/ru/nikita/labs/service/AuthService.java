package ru.nikita.labs.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.nikita.labs.dto.auth.JwtResponse;
import ru.nikita.labs.dto.auth.LoginRequest;
import ru.nikita.labs.dto.auth.RegisterRequest;
import ru.nikita.labs.dto.auth.UserDto;
import ru.nikita.labs.exception.AuthException;
import ru.nikita.labs.exception.message.AuthMessage;
import ru.nikita.labs.model.User;
import ru.nikita.labs.repository.UserRepository;
import ru.nikita.labs.util.Crypto;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService;

    public UserDto register(RegisterRequest userReq,
                            HttpServletRequest req) throws AuthException {
        checkUsername(userReq.getUsername());
        checkEmail(userReq.getEmail());

        User newUser = buildUser(userReq);
        userRepository.save(newUser);

        return UserDto.builder()
                .username(newUser.getUsername())
                .email(newUser.getEmail())
                .birthday(newUser.getBirthday())
                .role(newUser.getRole())
                .build();
    }

    public JwtResponse login(LoginRequest userReq,
                             HttpServletResponse resp) throws AuthException {
        User user = userRepository.findByUsername(
                userReq.getUsername());
        if (user == null) {
            throw new AuthException(
                    AuthMessage.WRONG_USERNAME_OR_PASSWORD,
                    HttpStatus.BAD_REQUEST);
        }
        String encodedPassword = Crypto.sha256Hex(
                userReq.getPassword(),
                user.getSalt());
        if (!user.getPassword().equals(encodedPassword)) {
            throw new AuthException(
                    AuthMessage.WRONG_USERNAME_OR_PASSWORD,
                    HttpStatus.BAD_REQUEST);
        }

        String token = jwtService.generateToken(user);
        resp.setHeader("Authorization", "Bearer " + token);
        return new JwtResponse(token);
    }

    public UserDto me(HttpServletRequest req) throws AuthException {
        return new UserDto();
    }

    public void out(HttpServletRequest req,
                    HttpServletResponse resp) throws AuthException {
        String token = req.getHeader("Authorization");
        if (token == null) {
            throw new AuthException(
                    AuthMessage.NOT_AUTHORIZED,
                    HttpStatus.UNAUTHORIZED
            );
        }
        resp.setHeader("Authorization", "");
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
