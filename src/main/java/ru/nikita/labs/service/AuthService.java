package ru.nikita.labs.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nikita.labs.config.JwtConfig;
import ru.nikita.labs.dto.PermissionDto;
import ru.nikita.labs.dto.RoleDto;
import ru.nikita.labs.dto.UserDto;
import ru.nikita.labs.dto.mapper.UserMapper;
import ru.nikita.labs.dto.request.CookieRequest;
import ru.nikita.labs.dto.request.LoginRequest;
import ru.nikita.labs.dto.request.RegisterRequest;
import ru.nikita.labs.dto.request.UpdatePasswordRequest;
import ru.nikita.labs.dto.response.JwtResponse;
import ru.nikita.labs.exception.AuthException;
import ru.nikita.labs.exception.factory.AuthExceptionFactory;
import ru.nikita.labs.exception.factory.UserExceptionFactory;
import ru.nikita.labs.model.Permission;
import ru.nikita.labs.model.User;
import ru.nikita.labs.repository.UserRepository;
import ru.nikita.labs.util.Crypto;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static ru.nikita.labs.config.JwtConfig.ACCESS;
import static ru.nikita.labs.config.JwtConfig.REFRESH;
import static ru.nikita.labs.exception.factory.AuthExceptionFactory.*;

@Service
public class AuthService {
    private final UserService userService;
    private final JwtService jwtService;
    private final CookieService cookieService;
    private final UserRepository userRepository;
    private final JwtConfig jwtConfig;

    @Transactional
    public UserDto register(@Valid RegisterRequest userReq)
            throws AuthException {
        return userService.create(userReq);
    }

    @Transactional
    public JwtResponse login(
            @Valid LoginRequest loginReq,
            HttpServletResponse resp) throws AuthException {
        User user = authenticateUser(loginReq);
        String refreshToken = jwtService.generateRefreshToken(user);
        String accessToken = jwtService.generateAccessToken(user);
        setTokenCookies(accessToken, refreshToken, resp);
        return new JwtResponse(accessToken);
    }

    @Transactional
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

    @Transactional
    public UserDto updatePassword(
            HttpServletRequest req,
            @Valid UpdatePasswordRequest updatePasswordReq,
            Long id) {
        User user = userRepository.getReferenceById(id);
        validateUser(req, user);
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
        return UserMapper.toDto(user);
    }

    public boolean hasPermission(List<RoleDto> roles, String permissionCode) {
        List<String> userPermissions = roles.stream()
                .flatMap(role -> role.getPermissions().stream())
                .map(PermissionDto::getCode)
                .toList();
        return userPermissions.contains(permissionCode);
    }

    private void validateUser(HttpServletRequest req, User user) {
        String refresh = cookieService.getCookie(REFRESH, req)
                .orElseThrow(() ->
                        new NoSuchElementException("Куки не найден!"))
                .getValue();
        String currentUsername = jwtService.extractUsername(refresh);
        if (!currentUsername.equals(user.getUsername())) {
            throw unauthorized();
        }
    }

    private User authenticateUser(LoginRequest loginReq) {
        User user = userRepository
                .findByUsername(loginReq.getUsername())
                .orElseThrow(AuthExceptionFactory::wrongUsernameOrPassword);
        String encryptedPassword = Crypto.sha256Hex(
                loginReq.getPassword(), user.getSalt());
        if (!user.getPassword().equals(encryptedPassword)) {
            throw wrongUsernameOrPassword();
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
                       JwtConfig jwtConfig) {
        this.userService = userService;
        this.jwtService = jwtService;
        this.cookieService = cookieService;
        this.userRepository = userRepository;
        this.jwtConfig = jwtConfig;
    }
}
