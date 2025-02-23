package ru.nikita.labs.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nikita.labs.validation.Adult;
import ru.nikita.labs.validation.FieldsValueMatch;

import java.time.LocalDate;

import static ru.nikita.labs.exception.message.ValidationMessage.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldsValueMatch(field = "cPassword",
        fieldMatch = "password",
        message = C_PASSWORD_MUST_BE_EQUALS_PASSWORD)
public class RegisterRequest {

    @NotBlank(message = USERNAME_CANNOT_BE_NULL)
    @Size(min = 7, max = 30, message = WRONG_USERNAME_SIZE)
    @Pattern(regexp = "^[A-Z]+[A-Za-z]*",
            message = WRONG_USERNAME_PATTERN)
    @JsonProperty("username")
    private String username;

    @NotBlank(message = PASSWORD_CANNOT_BE_NULL)
    @Size(min = 8, max = 30, message = WRONG_PASSWORD_SIZE)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).+$",
            message = WRONG_PASSWORD_PATTERN)
    @JsonProperty("password")
    private String password;

    @JsonProperty("c_password")
    private String cPassword;

    @NotBlank(message = EMAIL_CANNOT_BE_NULL)
    @Email(message = WRONG_EMAIL_FORMAT)
    @JsonProperty("email")
    private String email;

    @NotNull(message = BIRTHDAY_CANNOT_BE_NULL)
    @Adult(age = 14, message = BIRTHDAY_MUST_BE_GOE_FOURTEEN)
    @JsonProperty("birthday")
    private LocalDate birthday;
}
