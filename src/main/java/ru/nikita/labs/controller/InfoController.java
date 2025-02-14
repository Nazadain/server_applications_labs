package ru.nikita.labs.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nikita.labs.dto.ClientInfoDto;
import ru.nikita.labs.dto.DatabaseInfoDto;
import ru.nikita.labs.dto.ServerInfoDto;
import ru.nikita.labs.service.InfoService;

@RestController
@RequestMapping("/info")
public class InfoController {

    private final InfoService infoService;

    public InfoController(InfoService infoService) {
        this.infoService = infoService;
    }

    @GetMapping("/server")
    public ServerInfoDto server() {
        return infoService.getServerInfo();
    }

    @GetMapping("/client")
    public ClientInfoDto client(HttpServletRequest request) {
        return infoService.getClientInfo(request);
    }

    @GetMapping("/database")
    public DatabaseInfoDto database() {
        return infoService.getDatabaseInfo();
    }
}
