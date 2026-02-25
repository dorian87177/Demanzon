package com.demanzon.app.repository;

import java.util.Map;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.Properties;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.demanzon.app.DTO.DTOActualizacion;
import com.demanzon.app.DTO.DTOPing;
import com.demanzon.app.DTO.DTOVersion;

@Component
public class ClienteAPI {

    private final RestTemplate conexionAlAPI;
    private String urlBase = "https://dev.demanzon.com";
    private String urlVersion;
    private String urlActualizacion;
    private String urlVersionPro;
    private String urlActualizacionPro;
    private String urlPing;

    public ClienteAPI(RestTemplate conexionAlAPI) {
        this.conexionAlAPI = conexionAlAPI;
        String guardada = cargarUrlGuardada();
        if (guardada != null && !guardada.isBlank()) {
            this.urlBase = guardada;
        }
        reconstruirUrls();
    }

    private void reconstruirUrls() {
        this.urlVersion = urlBase + "/version";
        this.urlActualizacion = urlBase + "/updateSeason";
        this.urlVersionPro = urlBase + "/api/v1/animecalendarpro/appversion";
        this.urlActualizacionPro = urlBase + "/api/v1/animecalendarpro/updateAppVersion";
        this.urlPing = urlBase + "/ping";
    }

    public void establecerUrlBase(String nuevaUrl) {
        if (nuevaUrl == null || nuevaUrl.isBlank()) return;
        this.urlBase = nuevaUrl.trim();
        reconstruirUrls();
    }

    public String obtenerUrlBase() {
        return this.urlBase;
    }

    public void guardarUrlBase(String nuevaUrl) {
        try {
            Path rutaConfiguracion = obtenerRutaConfiguracion();
            Files.createDirectories(rutaConfiguracion.getParent());
            Properties propiedades = new Properties();
            propiedades.setProperty("api.url", nuevaUrl);
            try (var flujoSalida = Files.newOutputStream(rutaConfiguracion)) {
                propiedades.store(flujoSalida, "");
            }
        } catch (IOException e) {
        }
    }

    private String cargarUrlGuardada() {
        try {
            Path rutaConfiguracion = obtenerRutaConfiguracion();
            if (Files.exists(rutaConfiguracion)) {
                Properties propiedades = new Properties();
                try (var flujoEntrada = Files.newInputStream(rutaConfiguracion)) {
                    propiedades.load(flujoEntrada);
                }
                return propiedades.getProperty("api.url");
            }
        } catch (IOException e) {
        }
        return null;
    }

    private Path obtenerRutaConfiguracion() {
        String home = System.getProperty("user.home");
        Path dir = Paths.get(home, ".demanzon");
        return dir.resolve("config.properties");
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
