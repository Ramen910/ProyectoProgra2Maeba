package com.example.proyectoprogra2maeba.domain;


import java.util.ArrayList;
import java.util.List;

public class Aeropuerto {

    private String nombreAeropuerto;
    private List<Pista> pistas;
    private List<Puente> puentes;
    private List<Vuelo> vuelosEntrantes;
    private List<Vuelo> vuelosSalientes;

    // Constructor
    public Aeropuerto(String nombreAeropuerto) {
        this.nombreAeropuerto = nombreAeropuerto;
        pistas = new ArrayList<>();
        puentes = new ArrayList<>();
        vuelosEntrantes = new ArrayList<>();
        vuelosSalientes = new ArrayList<>();
    }

    // Métodos para administrar las pistas
    public void agregarPista(Pista pista) {
        pistas.add(pista);
    }

    public void eliminarPista(Pista pista) {
        pistas.remove(pista);
    }

    public List<Pista> getPistas() {
        return pistas;
    }

    // Métodos para administrar los puentes
    public void agregarPuente(Puente puente) {
        puentes.add(puente);
    }

    public int hayPuenteDisponible() {
        for (int i = 0; i < puentes.size(); i++) {
            if (puentes.get(i).getEstado().equalsIgnoreCase("Libre")) {
                return i; // Hay un puente disponible
            }
        }
        return -1; // No hay puentes disponibles
    }

    public boolean desocuparPuente(Aeronave avion) {
        for (Puente puente : puentes) {
            if (puente.getAvion() == avion) {
                puente.setEstado("Libre");
                puente.setAvion(null);
                return true;
            }
        }
        return false;
    }

    public boolean desocuparPista(Aeronave avion) {
        for (Pista pista : pistas) {
            if (pista.getAvion() == avion) {
                pista.setEstado("Libre");
                pista.setAvion(null);
                return true;
            }
        }
        return false;
    }

    public int pistaDisponible() {
        for (int i = 0; i < pistas.size(); i++) {
            if (pistas.get(i).getEstado().equalsIgnoreCase("Libre")) {
                return i; // Hay un puente disponible
            }
        }
        return -1; // No hay puentes disponibles
    }

    public String printPuentes(){
        String result = "";
        for (Puente puente : puentes) {
            result += puente.toString()+"\n";
        }
        return result;
    }

    public void printPistas(){
        for (Pista pista : pistas) {
            System.out.println(pista.toString());
        }
    }


    public List<Puente> getPuentes() {
        return puentes;
    }

    // Métodos para administrar los vuelos entrantes y salientes
    public void agregarVueloEntrante(Vuelo vuelo) {
        vuelosEntrantes.add(vuelo);
    }

    public void agregarVueloSaliente(Vuelo vuelo) {
        vuelosSalientes.add(vuelo);
    }

    public void imprimirListaSaliente(){
        for (int i = 0; i < vuelosSalientes.size(); i++) {
            
        }
    }
    
    public void eliminarVueloEntrante(Vuelo vuelo) {
        vuelosEntrantes.remove(vuelo);
    }

    public void eliminarVueloSaliente(Vuelo vuelo) {
        vuelosSalientes.remove(vuelo);
    }

    public List<Vuelo> getVuelosEntrantes() {
        return vuelosEntrantes;
    }

    public List<Vuelo> getVuelosSalientes() {
        return vuelosSalientes;
    }

    public  void ocuparPuente(int puente,Aeronave avion){
        this.puentes.get(puente).setAvion(avion);
        this.puentes.get(puente).setEstado("Ocupado");
    }

    public  void ocuparPista(int pista,Aeronave avion){
        this.pistas.get(pista).setAvion(avion);
        this.pistas.get(pista).setEstado("Ocupado");
    }

}