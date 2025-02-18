package ru.nikita.labs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nikita.labs.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
}
