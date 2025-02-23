package ru.nikita.labs.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import static ru.nikita.labs.exception.message.ValidationMessage.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @NotBlank(message = USERNAME_CANNOT_BE_NULL)
    @JsonProperty("username")
    private String username;

    @NotBlank(message = "Пароль не может быть пустым!")
    @JsonProperty("password")
    private String password;
}
