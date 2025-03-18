package ru.nikita.labs.dto.mapper;

import lombok.experimental.UtilityClass;
import ru.nikita.labs.dto.RoleDto;
import ru.nikita.labs.dto.request.RoleRequest;
import ru.nikita.labs.model.Role;

import java.time.LocalDateTime;

@UtilityClass
public class RoleMapper {

    public static RoleDto toDto(Role role) {
        return RoleDto.builder()
                .name(role.getName())
                .description(role.getDescription())
                .createdAt(role.getCreatedAt())
                .build();
    }

    public static Role toEntity(RoleRequest roleRequest) {
        return Role.builder()
                .name(roleRequest.getName())
                .code(roleRequest.getCode())
                .description(roleRequest.getDescription())
                .createdAt(LocalDateTime.now())
                .createdBy(roleRequest.getCreatedBy())
                .build();
    }
}
