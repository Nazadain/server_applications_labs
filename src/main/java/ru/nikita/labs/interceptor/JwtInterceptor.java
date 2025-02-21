package ru.nikita.labs.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import ru.nikita.labs.exception.AuthException;
import ru.nikita.labs.exception.message.AuthMessage;
import ru.nikita.labs.service.JwtService;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest req,
                             HttpServletResponse resp,
                             Object handler) throws AuthException {
        JwtService jwtService = new JwtService();
        String token = req.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            throw new AuthException(
                    AuthMessage.NOT_AUTHORIZED,
                    HttpStatus.UNAUTHORIZED);
        }
        token = token.split(" ")[1];
        if (jwtService.isTokenExpired(token)) {
            throw new AuthException(
                    AuthMessage.NOT_AUTHORIZED,
                    HttpStatus.UNAUTHORIZED);
        }
        return true;
    }
}
