package com.demanzon.app.DTO;

public class DTOPeticionActualizacion {

    private String version;

    public DTOPeticionActualizacion() {}

    public DTOPeticionActualizacion(String version) {
        this.version = version;
    }

    public String getVersion() { 
        return version; 
    }

    public void setVersion(String version) { 
        this.version = version; 
    }
    
}
