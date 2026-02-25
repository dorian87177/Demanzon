package com.demanzon.app.repository;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.demanzon.app.DTO.DTOActualizacion;
import com.demanzon.app.DTO.DTOPing;
import com.demanzon.app.DTO.DTOVersion;

@Component
public class ClienteAPI {

    private final RestTemplate conexionAlAPI;
    private final String urlVersion = "https://dev.demanzon.com/version";
    private final String urlActualizacion = "https://dev.demanzon.com/updateSeason";
    private final String urlVersionPremium = "https://dev.demanzon.com/api/v1/animecalendarpro/appversion";
    private final String urlActualizacionPremium = "https://dev.demanzon.com/api/v1/animecalendarpro/updateAppVersion";
    private final String urlPing = "https://dev.demanzon.com/ping";

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

    public DTOVersion obtenerVersionPremium() {
        return conexionAlAPI.getForObject(urlVersionPremium, DTOVersion.class);
    }

    public DTOActualizacion actualizarVersionPremium(String version) {
        String url = UriComponentsBuilder.fromUriString(urlActualizacionPremium)
                                         .queryParam("version", version)
                                         .toUriString();

        return conexionAlAPI.getForObject(url, DTOActualizacion.class);
    }

    public DTOPing ping() {
        String respuesta = conexionAlAPI.getForObject(urlPing, String.class);
        return parse(respuesta);
    }

    private static DTOPing parse(String linea) {

        DTOPing resultado = new DTOPing();

        if (linea.startsWith("ok")) {
            resultado.setOk(true);
        }

        int ultimoDosPuntos = linea.lastIndexOf(":");

        if (ultimoDosPuntos != -1) {
            String valor = linea.substring(ultimoDosPuntos + 1).trim();
            resultado.setEntorno(valor);
        }

        return resultado;
    }
}
