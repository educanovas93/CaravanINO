package com.caravaino.model;

public class Temperatura {

    private int id;
    private String nombre;
    private int valor;
    public Temperatura (int id,String nombre,int valor){
        this.id = id;
        this.nombre = nombre;
        this.valor = valor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getValor() {
        return valor/10;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }
}
