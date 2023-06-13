package com.example.proyectoprogra2maeba.domain;

public class Puente {
    private int idPuente;
    private String estado;
    private Aeronave current;

    public Puente(int idPuente, String estado) {
        this.idPuente = idPuente;
        this.estado = estado;
    }

    public int getIdPuente() {
        return idPuente;
    }

    public void setIdPuente(int idPuente) {
        this.idPuente = idPuente;
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
        return "Puente{" +
                "idPuente=" + idPuente +
                ", estado='" + estado + '\'' +
                '}';
    }
}
