package ru.nikita.labs.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.nikita.labs.dto.request.CookieRequest;
import ru.nikita.labs.exception.AuthException;
import ru.nikita.labs.exception.message.AuthMessage;

import java.util.Arrays;
import java.util.Optional;

@Service
public class CookieService {

    public void setCookie(CookieRequest cookieRequest,
                          HttpServletResponse resp) {
        String key = cookieRequest.getKey();
        String value = cookieRequest.getValue();
        Cookie cookie = new Cookie(key, value);

        cookie.setMaxAge(cookieRequest.getAge());
        cookie.setPath("/");

        resp.addCookie(cookie);
    }

    public Optional<Cookie> getCookie(String name, HttpServletRequest req) {
        if (req.getCookies() != null) {
            return Arrays.stream(req.getCookies())
                    .filter(cookie -> cookie.getName().equals(name))
                    .findFirst();
        }
        return Optional.empty();
    }

    public void deleteCookie(String cookieName,
                             HttpServletResponse resp) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        resp.addCookie(cookie);
    }
}
