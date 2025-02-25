package ru.nikita.labs.dto.mapper;

import org.springframework.stereotype.Component;
import ru.nikita.labs.dto.UserDto;
import ru.nikita.labs.dto.request.RegisterRequest;
import ru.nikita.labs.model.User;

@Component
public class UserMapper {

    public User getUserFromRegisterRequest(RegisterRequest registerRequest) {
        return User.builder()
                .username(registerRequest.getUsername())
                .password(registerRequest.getPassword())
                .email(registerRequest.getEmail())
                .birthday(registerRequest.getBirthday())
                .build();
    }

    public UserDto getUserDtoFromUser(User user) {
        return UserDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .birthday(user.getBirthday())
                .build();
    }
}
