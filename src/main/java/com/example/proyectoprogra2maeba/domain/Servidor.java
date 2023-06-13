package com.example.proyectoprogra2maeba.domain;

import com.example.proyectoprogra2maeba.logic.ServidorATC;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

    public static void main(String[] args) throws IOException {
        ServidorATC server = new ServidorATC();
        server.iniciarColaDespegue();
        server.iniciarColaAterrizaje();
        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("Servidor iniciado. Esperando conexiones...");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Cliente conectado: " + clientSocket.getInetAddress().getHostAddress());
            Thread thread = new Thread(() -> {
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

                    // Leer la solicitud del cliente
                    String option= in.readLine();
                    System.out.print(option);
                    if (option.equalsIgnoreCase("SOLICITAR_PUENTE")) {
                        out.println("SOLICITAR_CODIGO");
                        String id = in.readLine();
                        int result = server.pedirPuente(id);
                        if(result == 1){
                            out.println("AUTORIZED");
                        }else if(result == 0){
                            out.println("UNAUTORIZED");
                        }else{
                            out.println("NOT_FOUND");
                        }
                    } else if (option.equalsIgnoreCase("SOLICITAR_PISTA")) {
                        out.println("SOLICITAR_CODIGO");
                        String id = in.readLine();
                        int result = server.pedirPista(id);
                        if(result == 1){
                            out.println("AUTORIZED");
                        }else if(result == 0){
                            out.println("UNAUTORIZED");
                        }else{
                            out.println("NOT_FOUND");
                        }
                    } else if (option.equalsIgnoreCase("SOLICITAR_DESPEGUE")) {
                        out.println("SOLICITAR_CODIGO");
                        String id = in.readLine();
                        Thread despegue = new Thread(() -> {
                            int result = server.pedirDespegue(id);
                            if(result == 1){
                                out.println("AUTORIZED");
                                while (!server.despegueRealizado.equals(id)) {
                                    try {
                                        Thread.sleep(1000); // Pausa de 100 milisegundos
                                    } catch (InterruptedException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                                out.println("READY");
                            }else if(result == 0){
                                out.println("UNAUTORIZED");
                            }else{
                                out.println("NOT_FOUND");
                            }
                        });
                        despegue.start();
                    } else if (option.equalsIgnoreCase("SOLICITAR_ATERRIZAJE")) {
                        out.println("SOLICITAR_CODIGO");
                        String id = in.readLine();
                        server.pedirAterrizaje(id);
                    }else if(option.equalsIgnoreCase("INFO_VUELO")){

                        out.println("SOLICITAR_CODIGO");
                        String id= in.readLine();
                        String respuesta = server.pedirInfo(id);
                        if(respuesta.length()>0){
                            out.println(respuesta);
                        }else{
                            out.println("NOT_FOUND");
                        }

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            thread.start();

        }
    }
}
