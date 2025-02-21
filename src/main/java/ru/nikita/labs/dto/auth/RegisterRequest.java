package ru.nikita.labs.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nikita.labs.model.Role;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {

    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;

    @JsonProperty("c_password")
    private String cPassword;

    @JsonProperty("email")
    private String email;

    @JsonProperty("birthday")
    private LocalDate birthday;

    @JsonProperty("role")
    private Role role;
}