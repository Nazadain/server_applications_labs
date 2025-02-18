package ru.nikita.labs.dto.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequest implements UserRequest {

    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;
}
