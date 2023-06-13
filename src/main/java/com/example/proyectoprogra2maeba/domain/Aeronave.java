package com.example.proyectoprogra2maeba.domain;

public class Aeronave {
    private String placa;
    private String estado;
    private String tipo;

    public Aeronave(String placa, String tipo) {
        this.placa = placa;
        this.tipo = tipo;
        this.estado = " "; // Estado inicial por defecto
    }

    @Override
    public String toString() {
        return "Aeronave{" +
                "placa='" + placa + '\'' +
                ", estado='" + estado + '\'' +
                ", tipo='" + tipo + '\'' +
                '}';
    }

    public String getPlaca() {
        return placa;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTipo() {
        return tipo;
    }
}
