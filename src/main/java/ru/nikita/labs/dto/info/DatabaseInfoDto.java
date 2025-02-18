package ru.nikita.labs.dto.info;

public record DatabaseInfoDto(String productName,
                              String productVersion,
                              String driverName,
                              String driverVersion) {
}
