package com.example.actividad3_8.actividad3;

import java.io.Serializable;

public class Curso implements Serializable {
    String id;
    String descripcion;

    public Curso() {

    }

    public Curso(String id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public Curso(String id) {
        this.id = id;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Curso{" +
                "id='" + id + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
