package com.caravaino.model;

public class Luz {

    private boolean isTurned;
    private String nombre;
    private int id;
    private int intensidad;
    public Luz(int id,boolean isTurned,int intensidad){
        this.id = id;
        this.isTurned = isTurned;
        this.intensidad = intensidad;
        this.nombre = "Luz"+id;
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
        return ("#L"+id+" "+(isTurned ? "1":"0")+" "+intensidad);
    }
    public void setIntensidad(int intensidad){
        this.intensidad = intensidad;
    }
}
