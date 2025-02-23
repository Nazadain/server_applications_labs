package ru.nikita.labs.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nikita.labs.validation.FieldsValueMatch;

import static ru.nikita.labs.exception.message.ValidationMessage.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePasswordRequest {

    @NotBlank(message = PASSWORD_CANNOT_BE_NULL)
    @JsonProperty("old_password")
    private String oldPassword;

    @NotBlank(message = PASSWORD_CANNOT_BE_NULL)
    @Size(min = 8, max = 30, message = WRONG_PASSWORD_SIZE)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$",
            message = WRONG_PASSWORD_PATTERN)
    @JsonProperty("new_password")
    private String newPassword;
}
