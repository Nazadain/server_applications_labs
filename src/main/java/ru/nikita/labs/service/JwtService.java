package ru.nikita.labs.service;

import org.springframework.stereotype.Service;

@Service
public class JwtService {
    private final String SECRET_KEY = "J10nS3c43t53y";
    private final long EXPIRATION_TIME = 1000 * 60 * 60;


}
