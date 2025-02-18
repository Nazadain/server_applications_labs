package ru.nikita.labs.util;

import lombok.experimental.UtilityClass;
import ru.nikita.labs.dto.info.DatabaseInfoDto;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

@UtilityClass
public class DatabaseUtil {
    public static DatabaseInfoDto getDatabaseData(String url,
                                                  String username,
                                                  String password) {
        try (Connection connection = DriverManager.getConnection(url,
                username,
                password)) {
            DatabaseMetaData metaData = connection.getMetaData();

            String databaseProductName = metaData.getDatabaseProductName();
            String databaseProductVersion = metaData.getDatabaseProductVersion();
            String driverName = metaData.getDriverName();
            String driverVersion = metaData.getDriverVersion();

            return new DatabaseInfoDto(databaseProductName,
                    databaseProductVersion,
                    driverName,
                    driverVersion);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
