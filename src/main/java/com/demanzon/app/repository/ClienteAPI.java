package com.demanzon.app.repository;

import java.util.Map;

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
    private final String urlVersionPro = "https://dev.demanzon.com/api/v1/animecalendarpro/appversion";
    private final String urlActualizacionPro = "https://dev.demanzon.com/api/v1/animecalendarpro/updateAppVersion";

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

        Object respuesta = conexionAlAPI.getForObject(url, Object.class);
        return mapearActualizacion(respuesta);
    }

    public DTOVersion obtenerVersionPro() {
        return conexionAlAPI.getForObject(urlVersionPro, DTOVersion.class);
    }

    public DTOActualizacion actualizarVersionPro(String version) {
        String url = UriComponentsBuilder.fromUriString(urlActualizacionPro)
                .queryParam("version", version)
                .toUriString();

        Object respuesta = conexionAlAPI.getForObject(url, Object.class);
        return mapearActualizacion(respuesta);
    }

    private DTOActualizacion mapearActualizacion(Object respuesta) {
        DTOActualizacion dto = new DTOActualizacion();

        if (respuesta instanceof Boolean exito) {
            dto.setActualizacionExitosa(exito);
            return dto;
        }

        if (respuesta instanceof Map<?, ?> mapaRespuesta) {
            Object valorExito = mapaRespuesta.get("actualizacionExitosa");

            if (valorExito == null) {
                valorExito = mapaRespuesta.get("success");
            }

            if (valorExito == null) {
                valorExito = mapaRespuesta.get("ok");
            }

            if (valorExito instanceof Boolean exito) {
                dto.setActualizacionExitosa(exito);
                return dto;
            }
        }

        dto.setActualizacionExitosa(false);
        return dto;
    }
}
