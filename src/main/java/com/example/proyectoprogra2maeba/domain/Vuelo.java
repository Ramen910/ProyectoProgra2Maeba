package com.example.proyectoprogra2maeba.domain;

public class Vuelo {
    private String codigo;
    private String horaSalida;
    private String horaLlegada;
    private String origen;
    private String destino;
    private String estado;
    private String accionInteres;

    private Aeronave avion;

    public Vuelo(String codigo, String horaSalida, String horaLlegada, String origen, String destino, Aeronave avion) {
        this.codigo = codigo;
        this.horaSalida = horaSalida;
        this.horaLlegada = horaLlegada;
        this.origen = origen;
        this.destino = destino;
        this.estado = "En Espera"; // Estado inicial por defecto
        this.accionInteres = ""; // Acción de interés inicial vacía
        this.avion = avion;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getHoraSalida() {
        return horaSalida;
    }

    public String getHoraLlegada() {
        return horaLlegada;
    }

    public String getAeropuertoOrigen() {
        return origen;
    }

    public String getAeropuertoDestino() {
        return destino;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getAccionInteres() {
        return accionInteres;
    }

    public void setAccionInteres(String accionInteres) {
        this.accionInteres = accionInteres;
    }

    public Aeronave getAvion() {
        return avion;
    }

    public void setAvion(Aeronave avion) {
        this.avion = avion;
    }

    @Override
    public String toString() {
        return "Vuelo{" +
                "codigo='" + codigo + '\'' +
                ", horaSalida='" + horaSalida + '\'' +
                ", horaLlegada='" + horaLlegada + '\'' +
                ", origen='" + origen + '\'' +
                ", destino='" + destino + '\'' +
                ", estado='" + estado + '\'' +
                ", accionInteres='" + accionInteres + '\'' +
                ", avion=" + avion +
                '}';
    }
}
