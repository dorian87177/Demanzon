package com.demanzon.app.DTO;

public class DTOPing {

    private boolean ok;
    private String entorno;

    public DTOPing() {}

    public DTOPing(boolean ok, String entorno) {
        this.ok = ok;
        this.entorno = entorno;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public String getEntorno() {
        return entorno;
    }

    public void setEntorno(String entorno) {
        this.entorno = entorno;
    }

}
