package ru.nikita.labs.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import ru.nikita.labs.dto.ClientInfoDto;
import ru.nikita.labs.dto.DatabaseInfoDto;
import ru.nikita.labs.dto.ServerInfoDto;
import ru.nikita.labs.util.DatabaseUtil;

import java.util.Locale;

@Service
public class InfoService {

    @Autowired
    private Environment env;

    public ServerInfoDto getServerInfo() {
        String javaVersion = System.getProperty("java.version");
        String timeZone = System.getProperty("user.timezone");
        String locale = Locale.getDefault().toString();
        return new ServerInfoDto(javaVersion, timeZone, locale);
    }

    public ClientInfoDto getClientInfo(HttpServletRequest request) {
        String clientIp = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");
        return new ClientInfoDto(clientIp, userAgent);
    }

    public DatabaseInfoDto getDatabaseInfo() {
        String url = env.getProperty("spring.datasource.url");
        String username = env.getProperty("spring.datasource.username");
        String password = env.getProperty("spring.datasource.password");
        return DatabaseUtil.getDatabaseData(url, username, password);
    }
}
