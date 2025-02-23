package ru.nikita.labs.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.nikita.labs.dto.response.Response;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto implements Response {
    private String username;
    private String email;
    private LocalDate birthday;
}
