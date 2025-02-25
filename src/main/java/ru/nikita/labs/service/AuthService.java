package ru.nikita.labs.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nikita.labs.config.JwtConfig;
import ru.nikita.labs.dto.UserDto;
import ru.nikita.labs.dto.mapper.UserMapper;
import ru.nikita.labs.dto.request.CookieRequest;
import ru.nikita.labs.dto.request.LoginRequest;
import ru.nikita.labs.dto.request.RegisterRequest;
import ru.nikita.labs.dto.response.JwtResponse;
import ru.nikita.labs.exception.AuthException;
import ru.nikita.labs.exception.factory.AuthExceptionFactory;
import ru.nikita.labs.model.User;
import ru.nikita.labs.repository.UserRepository;
import ru.nikita.labs.util.Crypto;

import static ru.nikita.labs.config.JwtConfig.ACCESS;
import static ru.nikita.labs.config.JwtConfig.REFRESH;
import static ru.nikita.labs.exception.factory.AuthExceptionFactory.badCredentials;

@Service
public class AuthService {
    private final UserService userService;
    private final JwtService jwtService;
    private final CookieService cookieService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtConfig jwtConfig;

    public UserDto register(@Valid RegisterRequest userReq)
            throws AuthException {
        return userService.create(userReq);
    }

    @Transactional
    public JwtResponse login(
            @Valid LoginRequest userReq,
            HttpServletResponse resp) throws AuthException {
        User user = authenticateUser(userReq);
        UserDto userDto = userMapper.getUserDtoFromUser(user);
        String refreshToken = jwtService.generateRefreshToken(userDto);
        String accessToken = jwtService.generateAccessToken(userDto);
        setTokenCookies(accessToken, refreshToken, resp);
        return new JwtResponse(accessToken);
    }

    public UserDto me(HttpServletRequest req) throws AuthException {
        String token = cookieService.getCookie(ACCESS, req)
                .orElseThrow(AuthExceptionFactory::unauthorized)
                .getValue();
        return jwtService.extractUserDto(token);
    }

    public void logout(HttpServletResponse resp) {
        cookieService.deleteCookie(REFRESH, resp);
        cookieService.deleteCookie(ACCESS, resp);
    }

    public String refresh(
            HttpServletRequest req,
            HttpServletResponse resp) throws AuthException {
        String refreshToken = cookieService.getCookie(REFRESH, req)
                .orElseThrow(AuthExceptionFactory::unauthorized)
                .getValue();
        UserDto user = jwtService.extractUserDto(refreshToken);
        String newAccessToken = jwtService
                .generateAccessToken(user);
        resp.setHeader("Authorization", "Bearer " + newAccessToken);
        cookieService.setCookie(new CookieRequest(
                        ACCESS,
                        newAccessToken,
                        jwtConfig.getCookieAccessExpiration()),
                resp);
        return newAccessToken;
    }

    private User authenticateUser(LoginRequest userReq) {
        User user = userRepository
                .findByUsername(userReq.getUsername())
                .orElseThrow(AuthExceptionFactory::badCredentials);
        String encryptedPassword = Crypto.sha256Hex(
                userReq.getPassword(), user.getSalt());
        if (!user.getPassword().equals(encryptedPassword)) {
            throw badCredentials();
        }
        return user;
    }

    private void setTokenCookies(String accessToken,
                                 String refreshToken,
                                 HttpServletResponse resp) {
        CookieRequest refreshCookie = new CookieRequest(
                REFRESH,
                refreshToken,
                jwtConfig.getCookieRefreshExpiration());
        CookieRequest accessCookie = new CookieRequest(
                ACCESS,
                accessToken,
                jwtConfig.getCookieAccessExpiration());
        cookieService.setCookie(refreshCookie, resp);
        cookieService.setCookie(accessCookie, resp);
    }

    @Autowired
    public AuthService(UserService userService,
                       JwtService jwtService,
                       CookieService cookieService,
                       UserRepository userRepository,
                       UserMapper userMapper,
                       JwtConfig jwtConfig) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.cookieService = cookieService;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.jwtConfig = jwtConfig;
    }
}
