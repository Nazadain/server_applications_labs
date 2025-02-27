package ru.nikita.labs.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import static ru.nikita.labs.exception.message.ValidationMessage.PASSWORD_CANNOT_BE_NULL;
import static ru.nikita.labs.exception.message.ValidationMessage.USERNAME_CANNOT_BE_NULL;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @NotBlank(message = USERNAME_CANNOT_BE_NULL)
    @JsonProperty("username")
    private String username;

    @NotBlank(message = PASSWORD_CANNOT_BE_NULL)
    @JsonProperty("password")
    private String password;
}
