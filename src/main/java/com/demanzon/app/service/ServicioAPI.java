package com.demanzon.app.service;

import org.springframework.stereotype.Service;

import com.demanzon.app.DTO.DTOActualizacion;
import com.demanzon.app.DTO.DTOVersion;
import com.demanzon.app.repository.ClienteAPI;

@Service
public class ServicioAPI {

    private final ClienteAPI clienteAPI;

    public ServicioAPI(ClienteAPI clienteAPI) {
        this.clienteAPI = clienteAPI;
    }

    public DTOVersion obtenerVersion() {
        return clienteAPI.obtenerVersion();
    }

    public DTOActualizacion obtenerActualizacion(String version) {
        return clienteAPI.actualizarVersion(version);
    }

    public DTOVersion obtenerVersionPro() {
        return clienteAPI.obtenerVersionPro();
    }

    public DTOActualizacion obtenerActualizacionPro(String version) {
        return clienteAPI.actualizarVersionPro(version);
    }
}
