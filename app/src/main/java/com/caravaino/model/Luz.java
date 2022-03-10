package com.caravaino.model;

public class Luz {

    private boolean isTurned;
    private String nombre;
    private int id;
    public Luz(int id,boolean isTurned,int intensidad){

    }
    public Luz(String nombre, boolean isTurned){
        this.nombre = nombre;
        this.isTurned = isTurned;
    }
    public boolean isTurned() {
        return isTurned;
    }

    public void setTurned(boolean turned) {
        isTurned = turned;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getStatus(){
        return ("#L"+id+" ");
    }
}
