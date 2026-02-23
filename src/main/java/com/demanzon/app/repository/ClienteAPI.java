package com.demanzon.app.repository;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.demanzon.app.DTO.DTOActualizacion;
import com.demanzon.app.DTO.DTOVersion;

@Component
public class ClienteAPI {

    private final RestTemplate conexionAlAPI;
    private final String urlVersion = "https://dev.demanzon.com/version";
    private final String urlActualizacion = "https://dev.demanzon.com/updateSeason";

    public ClienteAPI(RestTemplate conexionAlAPI) {
        this.conexionAlAPI = conexionAlAPI;
    }

    public DTOVersion obtenerVersion() {
        return conexionAlAPI.getForObject(urlVersion, DTOVersion.class);
    }

    public DTOActualizacion actualizarVersion(String version) {
        String url = UriComponentsBuilder.fromUriString(urlActualizacion)
                                         .queryParam("version", version)
                                         .toUriString();

        return conexionAlAPI.getForObject(url, DTOActualizacion.class);
    }
}
