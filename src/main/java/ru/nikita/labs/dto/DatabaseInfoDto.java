package ru.nikita.labs.dto;

public record DatabaseInfoDto(String productName,
                              String productVersion,
                              String driverName,
                              String driverVersion) implements Dto {
}
