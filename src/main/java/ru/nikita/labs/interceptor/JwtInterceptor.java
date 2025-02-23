package ru.nikita.labs.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import ru.nikita.labs.dto.UserDto;
import ru.nikita.labs.exception.AuthException;
import ru.nikita.labs.exception.message.AuthMessage;
import ru.nikita.labs.service.CookieService;
import ru.nikita.labs.service.JwtService;

@Component
public class JwtInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private CookieService cookieService;
    private final String REGISTER_ENDPOINT = "/register";

    @Override
    public boolean preHandle(
            HttpServletRequest req,
            HttpServletResponse resp,
            Object handler) throws AuthException {
        String uri = req.getRequestURI();
        String refreshToken = getRefreshToken(req);
        if (uri.endsWith(REGISTER_ENDPOINT)) {
            return handleRegister(refreshToken);
        }
        String authHeader = resp.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            authHeader = refreshAccessToken(refreshToken, resp);
        }
        String accessToken = authHeader.substring(7);
        String username = jwtService.extractUsername(refreshToken);
        if (!jwtService.isTokenValid(accessToken, username)) {
            refreshAccessToken(refreshToken, resp);
        }
        return true;
    }

    private String getRefreshToken(HttpServletRequest req) {
        try {
            return cookieService
                    .getCookie("REFRESH", req)
                    .getValue();
        } catch (Exception e) {
            String uri = req.getRequestURI();
            if (uri.endsWith(REGISTER_ENDPOINT)) {
                return null;
            }
            throw new AuthException(
                    AuthMessage.NOT_AUTHORIZED,
                    HttpStatus.UNAUTHORIZED);
        }
    }

    private boolean handleRegister(String token) {
        if (token != null) {
            throw new AuthException(
                    AuthMessage.CANNOT_BE_AUTHORIZED,
                    HttpStatus.FORBIDDEN
            );
        }
        return true;
    }

    private String refreshAccessToken(String refreshToken,
                                      HttpServletResponse resp) {
        UserDto user = jwtService.extractUserResponse(refreshToken);
        String accessToken = jwtService.generateAccessToken(user);
        resp.addHeader("Authorization", "Bearer " + accessToken);
        return "Bearer " + accessToken;
    }
}
