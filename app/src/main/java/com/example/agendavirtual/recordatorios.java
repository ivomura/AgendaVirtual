package com.example.agendavirtual;

import java.io.Serializable;

public class recordatorios implements Serializable {
    private String nombrenota;
    private String descripcion;

    public String getNombrenota() {
        return nombrenota;
    }

    public void setNombrenota(String nombrenota) {
        this.nombrenota = nombrenota;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


}
