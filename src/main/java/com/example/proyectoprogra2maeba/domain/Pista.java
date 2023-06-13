package com.example.proyectoprogra2maeba.domain;

public class Pista {
    private int idPista;
    private String estado;

    private Aeronave current;

    public Pista(int idPista, String estado) {
        this.idPista = idPista;
        this.estado = estado;
    }

    public int getIdPista() {
        return idPista;
    }

    public void setIdPista(int idPista) {
        this.idPista = idPista;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Aeronave getAvion() {
        return current;
    }

    public void setAvion(Aeronave current) {
        this.current = current;
    }

    @Override
    public String toString() {
        String result = "Pista [" +
                "Código=" + idPista +
                ", Estado='" + estado + '\'';
        if(current!=null){
            result += ", Aeronave="+ getAvion().getPlaca();
        }
        result += ']';
        return result;
    }
}
