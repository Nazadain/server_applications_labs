package ru.nikita.labs.dto;

public record ClientInfoDto(String clientIp,
                            String userAgent) implements Dto {

}
