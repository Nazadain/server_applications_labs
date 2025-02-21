package ru.nikita.labs.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;
import ru.nikita.labs.dto.auth.UserDto;
import ru.nikita.labs.model.Role;
import ru.nikita.labs.model.User;

import java.time.LocalDate;
import java.util.Date;

@Service
public class JwtService {
    private final String SECRET_KEY =
            "3ee28689140b028d88559848a8293be208eae3d7d7dd83b76bd173eba6c61306";
    private final long EXPIRATION = 1000 * 60 * 60 * 24;

    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("email", user.getEmail())
                .claim("role", user.getRole().name())
                .claim("birthday", user.getBirthday().toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public UserDto extractUser(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getBody();
        System.out.println(claims);
        return buildUserDto(claims);
    }

    public boolean isTokenValid(String token, String username) {
        final String tokenUsername = extractUser(token).getUsername();
        return (tokenUsername.equals(username) && !isTokenExpired(token));
    }

    public boolean isTokenExpired(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getBody()
                .getExpiration()
                .before(new Date());
    }

    private UserDto buildUserDto(Claims claims) {
        return UserDto.builder()
                .username(claims.getSubject())
                .email(claims.get("email", String.class))
                .birthday(LocalDate.parse(claims.get(
                        "birthday", String.class)))
                .role(Role.valueOf(
                        claims.get("role", String.class)))
                .build();
    }
}
