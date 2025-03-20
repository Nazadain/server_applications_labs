package ru.nikita.labs.dto.mapper;

import lombok.experimental.UtilityClass;
import ru.nikita.labs.dto.PermissionDto;
import ru.nikita.labs.dto.request.PermissionRequest;
import ru.nikita.labs.model.Permission;

import java.time.LocalDateTime;

@UtilityClass
public class PermissionMapper {

    public static PermissionDto toDto(Permission permission) {
        return PermissionDto.builder()
                .name(permission.getName())
                .description(permission.getDescription())
                .code(permission.getCode())
                .createdAt(permission.getCreatedAt())
                .build();
    }

    public static Permission toEntity(PermissionRequest permissionRequest) {
        return Permission.builder()
                .name(permissionRequest.getName())
                .code(permissionRequest.getCode())
                .description(permissionRequest.getDescription())
                .createdBy(permissionRequest.getCreatedBy())
                .createdAt(LocalDateTime.now())
                .build();
    }
}
