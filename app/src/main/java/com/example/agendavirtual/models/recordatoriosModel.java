package com.example.agendavirtual.models;

import com.example.agendavirtual.recordatorios;

public class recordatoriosModel extends recordatorios {

        private String uid;
        private String nombre;
        private String descripcion;

    public recordatoriosModel() {
    }
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString(){
        return nombre + '\n' + descripcion + '\n';
    }


}
