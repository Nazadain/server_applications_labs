package ru.nikita.labs.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static ru.nikita.labs.config.JwtConfig.REFRESH;

@Service
public class JwtDataService {
    private final JwtService jwtService;
    private final CookieService cookieService;

    public Long getUserId(HttpServletRequest req) {
        return jwtService.extractId(getToken(req));
    }

    public String getUsername(HttpServletRequest req) {
        return jwtService.extractUsername(getToken(req));
    }

    public String getToken(HttpServletRequest req) {
        return cookieService.getCookie(REFRESH, req)
                .orElseThrow(() -> new RuntimeException("Cookie not found"))
                .getValue();
    }

    @Autowired
    public JwtDataService(JwtService jwtService,
                          CookieService cookieService) {
        this.jwtService = jwtService;
        this.cookieService = cookieService;
    }
}
