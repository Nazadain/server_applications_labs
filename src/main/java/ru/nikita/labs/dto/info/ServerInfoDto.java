package ru.nikita.labs.dto.info;

public record ServerInfoDto(String javaVersion,
                            String timeZone,
                            String locale) {
}
