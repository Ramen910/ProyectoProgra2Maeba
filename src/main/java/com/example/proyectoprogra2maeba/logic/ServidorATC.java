package com.example.proyectoprogra2maeba.logic;

import com.example.proyectoprogra2maeba.domain.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class ServidorATC {
    public volatile String despegueRealizado = "";
    public Aeropuerto aeropuerto;
    private Queue<Vuelo> colaDespegue = new ArrayBlockingQueue<>(20);
    private Queue<Vuelo> colaAterrizaje = new ArrayBlockingQueue<>(20);
    public List<Vuelo> vuelos = new ArrayList<>();

    public ServidorATC() {
        this.aeropuerto = new Aeropuerto("Juan Santa María");
        vuelos.add(new Vuelo("1", "10:00AM", "3:00PM", "Aeropuerto1", "Aeropuerto4", new Aeronave("12345", "Comercial")));
        vuelos.add(new Vuelo("2", "11:00AM", "9:00PM", "Aeropuerto2", "Aeropuerto3", new Aeronave("23456", "Avioneta")));
        vuelos.add(new Vuelo("3", "1:00AM", "1:00PM", "Aeropuerto3", "Aeropuerto2", new Aeronave("34567", "Comercial")));
        vuelos.add(new Vuelo("4", "7:00AM", "10:00AM", "Aeropuerto4", "Aeropuerto1", new Aeronave("45678", "Avioneta")));
        this.aeropuerto.agregarPista(new Pista(1, "Libre"));
        this.aeropuerto.agregarPista(new Pista(2, "Libre"));
        this.aeropuerto.agregarPista(new Pista(3, "Libre"));
        this.aeropuerto.agregarPuente(new Puente(1, "Libre"));
        this.aeropuerto.agregarPuente(new Puente(2, "Libre"));
        this.aeropuerto.agregarPuente(new Puente(3, "Libre"));
    }

    public String pedirInfo(String id){
        for (Vuelo vuelo : vuelos) {
            if (vuelo.getCodigo().equalsIgnoreCase(id)) {
                return vuelo.getHoraSalida() + "_" + vuelo.getHoraLlegada() + "_" + vuelo.getAeropuertoOrigen() + "_" + vuelo.getAeropuertoDestino() + "_" + vuelo.getAvion().getEstado();
            }
        }
        return "";
    }

    public int pedirPuente(String id) {
        int index = -1;
        for (int i = 0; i < vuelos.size(); i++) {
            if (vuelos.get(i).getCodigo().equalsIgnoreCase(id)) {
                index = i;
                break;
            }
        }
        if(index == -1) return -1;

        Aeronave aux = vuelos.get(index).getAvion();
        if (aux.getEstado().equalsIgnoreCase("En Pista")) {
            int puente = aeropuerto.hayPuenteDisponible();
            if (puente != -1) {
                aeropuerto.ocuparPuente(puente, aux);
                vuelos.get(index).getAvion().setEstado("En Puente");
                aeropuerto.desocuparPista(vuelos.get(index).getAvion());
                System.out.println("Moviendo a Puente");
                System.out.println("Puentes");
                aeropuerto.printPuentes();
                System.out.println("Pistas");
                aeropuerto.printPistas();
                return 1;
            } else {
                System.out.println("No hay puentes dispobles");
                return 0;
            }
        } else {
            System.out.println("Debe estar en Pista para pedir puente");
        }
        return -2;
    }

    public int pedirPista(String id) {
        int index = -1;
        for (int i = 0; i < vuelos.size(); i++) {
            if (vuelos.get(i).getCodigo().equalsIgnoreCase(id)) {
                index = i;
                break;
            }
        }
        if(index == -1) return -1;
        Aeronave aux = vuelos.get(index).getAvion();

        int pista = aeropuerto.pistaDisponible();
        if (pista != -1) {
            aeropuerto.ocuparPista(pista, aux);
            vuelos.get(index).getAvion().setEstado("En Pista");
            aeropuerto.desocuparPuente(vuelos.get(index).getAvion());
            System.out.println("Moviendo a Pista");
            System.out.println("Puentes");
            aeropuerto.printPuentes();
            System.out.println("Pistas");
            aeropuerto.printPistas();
            return 1;
        } else {
            System.out.println("No hay pistas dispobles");
            return 0;
        }
    }

    public int pedirDespegue(String id) {
        for (int i = 0; i < vuelos.size(); i++) {
            if (vuelos.get(i).getCodigo().equalsIgnoreCase(id)) {
                final int index = i;
                Aeronave aux = vuelos.get(index).getAvion();
                if (aux.getEstado().equalsIgnoreCase("En Pista")) {
                    new Thread(() -> {
                        int tiempoEspera = 0;
                        if (aux.getTipo().equalsIgnoreCase("Comercial")) {
                            tiempoEspera = 30000; // 2 segundos
                        } else if (aux.getTipo().equalsIgnoreCase("Avioneta")) {
                            tiempoEspera = 90000; // 1 segundo
                        } else if (aux.getTipo().equalsIgnoreCase("Carga")) {
                            tiempoEspera = 4000; // 4 segundos
                        }
                        vuelos.get(index).setEstado("En Espera");
                        System.out.println("Esperando Despegue" + vuelos.get(index).getCodigo());
                        try {
                            Thread.sleep(tiempoEspera); // Pausar la ejecución durante el tiempo de espera
                            colaDespegue.offer(vuelos.get(index));
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }

                    }).start();
                    return 1;
                } else {
                    System.out.println("No puede despegar si no está en pista");
                    return 0;
                }
            }
        }
        return -1;
    }

    public boolean pedirAterrizaje(String id) {
        int index = -1;
        for (int i = 0; i < vuelos.size(); i++) {
            if (vuelos.get(i).getCodigo().equalsIgnoreCase(id)) {
                index = i;
                break;
            }
        }
        Aeronave aux = vuelos.get(index).getAvion();
        if (aux.getEstado().equalsIgnoreCase("En Vuelo")) {
            colaAterrizaje.offer(vuelos.get(index));
            System.out.println("Esperando Aterrizaje");
            return true;
        } else {
            System.out.println("No puede aterrizar si no está en pista");
        }
        return false;
    }

    public void iniciarColaDespegue() {
        Thread colaDespegueThread = new Thread(() -> {
            while (true) {
                if (!colaDespegue.isEmpty()) {
                    Vuelo vuelo = colaDespegue.poll();
                    if (vuelo != null) {
                        vuelo.setEstado("Autorizado");
                        aeropuerto.agregarVueloSaliente(vuelo);
                        vuelo.getAvion().setEstado("En Vuelo");
                        aeropuerto.desocuparPista(vuelo.getAvion());
                        despegueRealizado = vuelo.getCodigo();
                        System.out.println("Puentes");
                        aeropuerto.printPuentes();
                        System.out.println("Pistas");
                        aeropuerto.printPistas();
                        System.out.println("Despegando vuelo " + vuelo.getCodigo());
                    }
                }

                // Pausar el hilo durante un tiempo determinado antes de revisar la cola nuevamente
                try {
                    Thread.sleep(1000); // Pausa de 1 segundo
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        colaDespegueThread.start();
    }

    public void iniciarColaAterrizaje() {
        Thread colaDespegueThread = new Thread(() -> {
            while (true) {
                if (!colaAterrizaje.isEmpty()) {
                    Vuelo vuelo = colaAterrizaje.poll();
                    if (vuelo != null) {
                        int pistaDisponible = aeropuerto.pistaDisponible();
                        while(pistaDisponible==-1){
                            vuelo.setEstado("En Espera");
                            try {
                                Thread.sleep(1000); // Pausa de 1 segundo
                                pistaDisponible = aeropuerto.pistaDisponible();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        vuelo.setEstado("Autorizado");
                        aeropuerto.agregarVueloEntrante(vuelo);
                        vuelo.getAvion().setEstado("En Pista");
                        aeropuerto.ocuparPista(pistaDisponible, vuelo.getAvion());
                        System.out.println("Puentes");
                        aeropuerto.printPuentes();
                        System.out.println("Pistas");
                        aeropuerto.printPistas();
                        System.out.println("Aterrizando vuelo " + vuelo.getCodigo());
                    }
                }

                // Pausar el hilo durante un tiempo determinado antes de revisar la cola nuevamente
                try {
                    Thread.sleep(1000); // Pausa de 1 segundo
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        colaDespegueThread.start();
    }

}
