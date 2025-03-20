package ru.nikita.labs.dto.mapper;

import lombok.experimental.UtilityClass;
import ru.nikita.labs.dto.RoleDto;
import ru.nikita.labs.dto.UserDto;
import ru.nikita.labs.dto.request.RegisterRequest;
import ru.nikita.labs.model.User;

import java.util.List;

@UtilityClass
public class UserMapper {

    public User toEntity(RegisterRequest registerRequest) {
        return User.builder()
                .username(registerRequest.getUsername())
                .password(registerRequest.getPassword())
                .email(registerRequest.getEmail())
                .birthday(registerRequest.getBirthday())
                .build();
    }

    public UserDto toDto(User user) {
        return UserDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .birthday(user.getBirthday())
                .roles(user.roles())
                .build();
    }
}
