package ru.nikita.labs.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nikita.labs.config.JwtConfig;
import ru.nikita.labs.dto.UserDto;
import ru.nikita.labs.dto.request.CookieRequest;
import ru.nikita.labs.exception.AuthException;
import ru.nikita.labs.exception.factory.AuthExceptionFactory;
import ru.nikita.labs.model.User;

import java.security.Key;
import java.time.LocalDate;
import java.util.Date;
import java.util.function.Function;

import static ru.nikita.labs.config.JwtConfig.ACCESS;
import static ru.nikita.labs.config.JwtConfig.REFRESH;

@Getter
@Service
public class JwtService {
    private final JwtConfig jwtConfig;
    private final CookieService cookieService;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Long extractId(String token) {
        return extractAllClaims(token).get("id", Long.class);
    }

    public UserDto extractUserDto(String token) {
        Claims claims = extractAllClaims(token);
        return UserDto.builder()
                .username(claims.getSubject())
                .email(claims.get("email", String.class))
                .birthday(LocalDate.parse(
                        claims.get("birthday", String.class)))
                .build();
    }

    public User extractUser(String token) {
        Claims claims = extractAllClaims(token);
        return User.builder()
                .username(claims.getSubject())
                .email(claims.get("email", String.class))
                .birthday(LocalDate.parse(
                        claims.get("birthday", String.class)))
                .id(claims.get("id", Long.class))
                .build();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parser()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String generateAccessToken(User user) {
        return buildToken(user, jwtConfig.getAccessExpiration());
    }

    public String generateRefreshToken(User user) {
        return buildToken(user, jwtConfig.getRefreshExpiration());
    }

    private String buildToken(User user, long expiration) {
        return Jwts
                .builder()
                .setSubject(user.getUsername())
                .claim("id", user.getId())
                .claim("email", user.getEmail())
                .claim("birthday", user.getBirthday().toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String refresh(
            HttpServletRequest req,
            HttpServletResponse resp) throws AuthException {
        String refreshToken = cookieService.getCookie(REFRESH, req)
                .orElseThrow(AuthExceptionFactory::unauthorized)
                .getValue();
        User user = extractUser(refreshToken);
        String newAccessToken = generateAccessToken(user);
        resp.setHeader(jwtConfig.AUTHORIZATION_HEADER,
                jwtConfig.BEARER_PREFIX + newAccessToken);
        cookieService.setCookie(new CookieRequest(
                        ACCESS,
                        newAccessToken,
                        jwtConfig.getCookieAccessExpiration()),
                resp);
        return newAccessToken;
    }

    public boolean isTokenValid(String token, String username) {
        final String tokenUsername = extractUsername(token);
        System.out.println("Token username: " + tokenUsername);
        System.out.println("Username: " + username);
        return tokenUsername.equals(username);
    }

    public boolean isTokenExpired(String token) {
        try {
            return extractExpiration(token).before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtConfig.getSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Autowired
    public JwtService(JwtConfig jwtConfig, CookieService cookieService) {
        this.jwtConfig = jwtConfig;
        this.cookieService = cookieService;
    }
}
