package com.example.agendavirtual.models;

public class Tarea {

    private String uid;
    private String nombre;
    private String fecha;
    private String hora;
    private String descripcion;

    public Tarea() {
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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return fecha + '\n'+
                hora + '\n' +
                "[NOMBRE] " + nombre + '\n' +
                "[DESCRIPCION] " + descripcion;
    }
}