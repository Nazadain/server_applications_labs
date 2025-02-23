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

@Service
public class CookieService {

    public void setCookie(CookieRequest cookieRequest,
                          HttpServletResponse resp) {
        String key = cookieRequest.getKey();
        String value = cookieRequest.getValue();
        Cookie cookie = new Cookie(key, value);

        cookie.setMaxAge(2592000);
        cookie.setPath("/");

        resp.addCookie(cookie);
    }

    public Cookie getCookie(String name, HttpServletRequest req) {
        Cookie[] cookies = req.getCookies();
        if (cookies == null) {
            throw new NullPointerException("Cookies is null");
        }
        Cookie cookie = Arrays.stream(cookies)
                .filter(c -> c.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new AuthException(
                        AuthMessage.NOT_AUTHORIZED,
                        HttpStatus.UNAUTHORIZED));
        if (cookie.getValue() == null || cookie.getValue().isEmpty()) {
            throw new NullPointerException("Cookie value is null");
        }
        return cookie;
    }

    public void deleteCookie(String cookieName,
                             HttpServletResponse resp) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        resp.addCookie(cookie);
    }
}
