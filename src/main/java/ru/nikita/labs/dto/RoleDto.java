package ru.nikita.labs.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleDto {
    private String name;
    private String description;
    private String code;
    private LocalDateTime createdAt;
    private List<PermissionDto> permissions;
}
