package ru.nikita.labs.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import ru.nikita.labs.exception.AuthException;
import ru.nikita.labs.exception.message.AuthMessage;
import ru.nikita.labs.service.AuthService;
import ru.nikita.labs.service.CookieService;
import ru.nikita.labs.service.JwtService;

import static ru.nikita.labs.config.JwtConfig.ACCESS;
import static ru.nikita.labs.config.JwtConfig.REFRESH;

@Component
public class JwtInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtService jwtService;
    @Autowired
    private CookieService cookieService;
    @Autowired
    private AuthService authService;

    private final String REGISTER_ENDPOINT = "/register";

    @Override
    public boolean preHandle(
            HttpServletRequest req,
            HttpServletResponse resp,
            Object handler) throws AuthException {
        String uri = req.getRequestURI();
        String refreshToken;
        try {
            getCookieValueByName(req, ACCESS);
            refreshToken = getCookieValueByName(req, REFRESH);
        } catch (AuthException e) {
            if (isAuthorizedProhibitedRoute(uri)) {
                return true;
            } else {
                throw e;
            }
        }
        if (isAuthorizedProhibitedRoute(uri)) {
            throw new AuthException(
                    AuthMessage.CANNOT_BE_AUTHORIZED,
                    HttpStatus.FORBIDDEN);
        }
        String username = jwtService.extractUsername(refreshToken);

        boolean isAccessHeaderValid = true;
        String accessHeader = resp.getHeader("Authorization");

        if (accessHeader != null && accessHeader.startsWith("Bearer ")) {
            accessHeader = accessHeader.substring(7);
            if (!jwtService.isTokenValid(accessHeader, username)) {
                isAccessHeaderValid = false;
            }
        }
        if (!isAccessHeaderValid) {
            authService.refresh(req, resp);
        }
        return true;
    }

    private boolean isAuthorizedProhibitedRoute(String uri) {
        return uri.endsWith(REGISTER_ENDPOINT);
    }

    private String getCookieValueByName(
            HttpServletRequest req, String name) {
        return cookieService.getCookie(name, req)
                .orElseThrow(() -> new AuthException(
                        AuthMessage.NOT_AUTHORIZED,
                        HttpStatus.UNAUTHORIZED))
                .getValue();
    }
}
