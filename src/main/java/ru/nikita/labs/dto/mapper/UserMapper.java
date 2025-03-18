package ru.nikita.labs.dto.mapper;

import lombok.experimental.UtilityClass;
import org.springframework.stereotype.Component;
import ru.nikita.labs.dto.UserDto;
import ru.nikita.labs.dto.request.RegisterRequest;
import ru.nikita.labs.model.User;

@UtilityClass
public class UserMapper {

    public User toUser(RegisterRequest registerRequest) {
        return User.builder()
                .username(registerRequest.getUsername())
                .password(registerRequest.getPassword())
                .email(registerRequest.getEmail())
                .birthday(registerRequest.getBirthday())
                .build();
    }

    public UserDto toUserDto(User user) {
        return UserDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .birthday(user.getBirthday())
                .build();
    }
}
