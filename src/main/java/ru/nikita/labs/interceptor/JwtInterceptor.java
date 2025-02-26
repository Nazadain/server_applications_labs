package ru.nikita.labs.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import ru.nikita.labs.config.JwtConfig;
import ru.nikita.labs.exception.AuthException;
import ru.nikita.labs.exception.factory.AuthExceptionFactory;
import ru.nikita.labs.service.CookieService;
import ru.nikita.labs.service.JwtService;

import java.util.List;

import static ru.nikita.labs.config.JwtConfig.REFRESH;
import static ru.nikita.labs.exception.factory.AuthExceptionFactory.cannotBeAuthorized;
import static ru.nikita.labs.exception.factory.AuthExceptionFactory.unauthorized;

@Component
public class JwtInterceptor implements HandlerInterceptor {
    private final JwtConfig jwtConfig;
    private final JwtService jwtService;
    private final CookieService cookieService;

    @Override
    public boolean preHandle(
            HttpServletRequest req,
            HttpServletResponse resp,
            Object handler) throws AuthException {
        String uri = req.getRequestURI();
        try {
            String refreshToken =
                    getCookieValueByName(req, REFRESH);
            if (!isAccessTokenValid(req, refreshToken)) {
                jwtService.refresh(req, resp);
            }
        } catch (AuthException e) {
            if (isRouteForNotAuthorized(uri)) {
                return true;
            } else {
                throw e;
            }
        }
        if (isRouteForNotAuthorized(uri)) {
            throw cannotBeAuthorized();
        }
        return true;
    }

    private boolean isAccessTokenValid(
            HttpServletRequest req,
            String refreshToken) throws AuthException {
        String accessHeader = req.getHeader(
                jwtConfig.AUTHORIZATION_HEADER);
        if (accessHeader == null) {
            throw unauthorized();
        }
        String username = jwtService.extractUsername(refreshToken);

        boolean isAccessHeaderValid = false;

        if (accessHeader.startsWith(jwtConfig.BEARER_PREFIX)) {
            accessHeader = accessHeader.substring(7);
            if (jwtService.isTokenValid(accessHeader, username)) {
                isAccessHeaderValid = true;
            }
        }
        return isAccessHeaderValid;
    }

    private boolean isRouteForNotAuthorized(String uri) {
        final List<String> endpoints = List.of(
                "/register");
        for (String endpoint : endpoints) {
            if (!uri.endsWith(endpoint)) {
                return false;
            }
        }
        return true;
    }

    private String getCookieValueByName(
            HttpServletRequest req, String name) throws AuthException {
        return cookieService.getCookie(name, req)
                .orElseThrow(AuthExceptionFactory::unauthorized)
                .getValue();
    }

    @Autowired
    public JwtInterceptor(JwtService jwtService,
                          CookieService cookieService,
                          JwtConfig jwtConfig) {
        this.jwtService = jwtService;
        this.cookieService = cookieService;
        this.jwtConfig = jwtConfig;
    }
}
