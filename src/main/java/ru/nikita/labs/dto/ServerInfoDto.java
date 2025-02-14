package ru.nikita.labs.dto;

public record ServerInfoDto(String javaVersion,
                            String timeZone,
                            String locale) implements Dto {
}
