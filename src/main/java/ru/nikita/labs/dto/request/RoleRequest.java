package ru.nikita.labs.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static ru.nikita.labs.exception.message.ValidationMessage.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleRequest {

    @NotBlank(message = NAME_CANNOT_BE_NULL)
    private String name;

    private String description;

    @NotBlank(message = CODE_CANNOT_BE_NULL)
    private String code;

    @NotNull(message = CREATED_BY_CANNOT_BE_NULL)
    @JsonProperty("created_by")
    private Long createdBy;
}
