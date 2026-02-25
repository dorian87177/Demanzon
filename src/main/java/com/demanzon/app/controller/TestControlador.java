package com.demanzon.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demanzon.app.DTO.DTOActualizacion;
import com.demanzon.app.DTO.DTOPing;
import com.demanzon.app.DTO.DTOVersion;
import com.demanzon.app.service.ServicioAPI;

@RestController
public class TestControlador {

    private final ServicioAPI servicioAPI;

    public TestControlador(ServicioAPI servicioAPI) {
        this.servicioAPI = servicioAPI;
    }

    @GetMapping("/version")
    public DTOVersion version() {
        return servicioAPI.obtenerVersion();
    }

    @GetMapping("/updateSeason")
    public DTOActualizacion actualizacion(@RequestParam String version) {
        return servicioAPI.obtenerActualizacion(version);
    }

    @GetMapping("/ping")
    public DTOPing ping() {
        return servicioAPI.obtenerPing();
    }

    @GetMapping("/api/v1/animecalendarpro/appversion")
    public DTOVersion versionPremium() {
        return servicioAPI.obtenerVersionPremium();
    }

    @GetMapping("/api/v1/animecalendarpro/updateAppVersion")
    public DTOActualizacion actualizacionPremium(@RequestParam String version) {
        return servicioAPI.obtenerActualizacionPremium(version);
    }
}
