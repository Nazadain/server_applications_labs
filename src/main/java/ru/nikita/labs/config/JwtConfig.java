package ru.nikita.labs.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class JwtConfig {
    public static final String ACCESS = "ACCESS_TOKEN";
    public static final String REFRESH = "REFRESH_TOKEN";
    public final String BEARER_PREFIX = "Bearer ";
    public final String AUTHORIZATION_HEADER = "Authorization";

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.access.expiration}")
    private long accessExpiration;

    @Value("${jwt.refresh.expiration}")
    private long refreshExpiration;

    @Value("${cookie.access.expiration}")
    private int cookieAccessExpiration;

    @Value("${cookie.refresh.expiration}")
    private int cookieRefreshExpiration;

}
