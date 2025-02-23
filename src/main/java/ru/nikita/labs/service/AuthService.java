package ru.nikita.labs.service;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.nikita.labs.dto.UserDto;
import ru.nikita.labs.dto.request.CookieRequest;
import ru.nikita.labs.dto.request.LoginRequest;
import ru.nikita.labs.dto.request.RegisterRequest;
import ru.nikita.labs.dto.response.JwtResponse;
import ru.nikita.labs.exception.AuthException;
import ru.nikita.labs.exception.message.AuthMessage;
import ru.nikita.labs.model.User;
import ru.nikita.labs.repository.UserRepository;
import ru.nikita.labs.util.Crypto;

import static ru.nikita.labs.exception.message.AuthMessage.USER_ALREADY_EXISTS;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private CookieService cookieService;

    public UserDto register(
            RegisterRequest userReq) throws AuthException {
        if (userRepository.existsByUsernameIgnoreCase(
                userReq.getUsername())) {
            throw new AuthException(
                    USER_ALREADY_EXISTS,
                    HttpStatus.CONFLICT);
        }
        User user = buildUser(userReq);
        userRepository.save(user);

        return buildUserDto(user);
    }

    public JwtResponse login(
            LoginRequest userReq,
            HttpServletResponse resp) throws AuthException {
        User user = userRepository
                .findByUsername(userReq.getUsername())
                .orElseThrow(() -> new AuthException(
                        AuthMessage.WRONG_USERNAME_OR_PASSWORD,
                        HttpStatus.BAD_REQUEST));
        String encryptedPassword = Crypto.sha256Hex(
                userReq.getPassword(), user.getSalt());
        if (!user.getPassword().equals(encryptedPassword)) {
            throw new AuthException(
                    AuthMessage.WRONG_USERNAME_OR_PASSWORD,
                    HttpStatus.BAD_REQUEST);
        }
        UserDto userDto = buildUserDto(user);
        String refreshToken = jwtService.generateRefreshToken(userDto);
        String accessToken = jwtService.generateAccessToken(userDto);
        CookieRequest cookieReq = new CookieRequest(
                "REFRESH", refreshToken);

        cookieService.setCookie(cookieReq, resp);
        resp.setHeader("Authorization", "Bearer " + accessToken);
        return new JwtResponse(accessToken);
    }

    public UserDto me(HttpServletResponse resp) {
        String token = resp.getHeader("Authorization")
                .substring(7);
        return jwtService.extractUserResponse(token);
    }

    public void logout(HttpServletResponse resp) {
        cookieService.deleteCookie("REFRESH", resp);
    }

    private UserDto buildUserDto(User user) {
        return UserDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .birthday(user.getBirthday())
                .build();
    }

    private User buildUser(RegisterRequest userReq) {
        return User.builder()
                .username(userReq.getUsername())
                .password(userReq.getPassword())
                .email(userReq.getEmail())
                .birthday(userReq.getBirthday())
                .build();
    }
}
