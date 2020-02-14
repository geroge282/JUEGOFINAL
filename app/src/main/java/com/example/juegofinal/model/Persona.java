package com.example.juegofinal.model;

public class Persona {


    private String uid;

    private String Nombre;
    private String Edad;
    private String Puntaje;

    public Persona() {
    }

    public String getEdad() {
        return Edad;
    }

    public void setEdad(String edad) {
        Edad = edad;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getPuntaje() {
        return Puntaje;
    }

    public void setPuntaje(String puntaje) {
        Puntaje = puntaje;
    }

    @Override
    public String toString() {
        return
                 Nombre  +"     edad:    "+ Edad +          "    Puntaje:  " + Puntaje ;
    }
}

