package com.caravaino.controller;

import android.bluetooth.BluetoothSocket;

public class Caravaino {

    private static Caravaino caravaino = null;
    BluetoothSocket btSocket = null;
    private String address;
    private Caravaino(){
    }
    public static Caravaino getUnicaInstancia(){
        if(caravaino == null) {
            caravaino = new Caravaino();
        }
        return caravaino;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BluetoothSocket getBtSocket() {
        return btSocket;
    }

    public void setBtSocket(BluetoothSocket btSocket) {
        this.btSocket = btSocket;
    }
}
