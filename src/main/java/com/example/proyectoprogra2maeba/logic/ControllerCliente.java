package com.example.proyectoprogra2maeba.logic;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ControllerCliente {
    @FXML
    private TextField txfCodigo;
    @FXML
    private TextField txfSalida;
    @FXML
    private TextField txfLlegada;
    @FXML
    private TextField txfEstado;
    @FXML
    private TextField txfOrigen;
    @FXML
    private TextField txfDestino;
    @FXML
    private TextArea txfEstadoActual;
    @FXML
    private TextField txfAccion;
    @FXML
    private Button btnConsultar;
    @FXML
    private Button btnDespegar;
    @FXML
    private Button btnAterrizar;
    @FXML
    private Button btnPuente;
    @FXML
    private Button btnPista;

    private boolean esperandoDespegue = false;

    // Método que se ejecuta al hacer clic en el botón "Consultar"
    @FXML
    private void consultarInformacion() {
        try {
            actualizarEstadoBotones(true);
            txfAccion.setText("Solicitando status");
            // Establecer conexión con el servidor
            Socket socket = new Socket("localhost", 8080);

            // Enviar la frase INFO_VUELO al servidor
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println("INFO_VUELO");

            // Leer la solicitud del servidor
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String solicitud = in.readLine();
            System.out.println(solicitud);
            // Verificar si el servidor solicitó el código del vuelo
            if (solicitud != null && solicitud.equals("SOLICITAR_CODIGO")) {
                // Obtener el código del vuelo ingresado por el usuario
                String codigoVuelo = txfCodigo.getText();
                // Enviar el código del vuelo al servidor
                out.println(codigoVuelo);
                // Leer la respuesta del servidor
                String respuesta = in.readLine();
                System.out.println(respuesta);
                // Cerrar conexión
                socket.close();

                // Actualizar los campos de texto con la respuesta del servidor
                if (!respuesta.equals("NOT_FOUND")) {
                    String[] campos = respuesta.split("_");
                    // Actualizar los campos de texto correspondientes
                    txfSalida.setText(campos[0]);
                    txfLlegada.setText(campos[1]);
                    txfOrigen.setText(campos[2]);
                    txfDestino.setText(campos[3]);
                    txfEstado.setText(campos[4]);
                    actualizarEstadoBotones(false);
                } else {
                    showErrorMessageDialog("No se encontró ese vuelo");
                }

            } else {
                showErrorMessageDialog("No se pudo terminar la comunicación con el servidor");
            }
        } catch (IOException e) {
            showErrorMessageDialog("Ocurrió un error, intente de nuevo");
        }
    }

    @FXML
    private void solicitarDespegue() {
        try {
            txfAccion.setText("Solicitando despegue");
            // Establecer conexión con el servidor
            Socket socket = new Socket("localhost", 8080);

            // Enviar la frase SOLICITAR_DESPEGUE al servidor
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println("SOLICITAR_DESPEGUE");

            // Leer la solicitud del servidor
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String solicitud = in.readLine();
            System.out.println(solicitud);
            // Verificar si el servidor solicitó el código del vuelo
            if (solicitud != null && solicitud.equals("SOLICITAR_CODIGO")) {
                // Obtener el código del vuelo ingresado por el usuario
                String codigoVuelo = txfCodigo.getText();
                // Enviar el código del vuelo al servidor
                out.println(codigoVuelo);
                // Leer la respuesta del servidor
                String respuesta = in.readLine();
                System.out.println(respuesta);
                // Cerrar conexión

                // Actualizar los campos de texto con la respuesta del servidor
                if (respuesta.equals("AUTORIZED")) {
                    esperandoDespegue = true;
                    new Thread(()->{
                        try {
                            String res = in.readLine();
                            if(res.equalsIgnoreCase("READY")){
                                txfEstado.setText("En Vuelo");
                                txfEstadoActual.setText("Despegue realizado con éxito...");
                            }else{
                                showErrorMessageDialog("No se pudo despegar");
                            }
                            esperandoDespegue = false;
                            socket.close();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }).start();
                    txfEstadoActual.setText("Autorizado, esperando despegue...");
                } else if (respuesta.equals("UNAUTORIZED")) {
                    txfEstadoActual.setText("No fue autorizado para despegar.");
                } else {
                    showErrorMessageDialog("No se encontró ese vuelo");
                }

            } else {
                showErrorMessageDialog("No se pudo terminar la comunicación con el servidor");
            }
        } catch (IOException e) {
            showErrorMessageDialog("Ocurrió un error, intente de nuevo");
        }
    }

    @FXML
    private void solicitarAterrizar() {
        try {
            txfAccion.setText("Solicitando aterrizaje");
            // Establecer conexión con el servidor
            Socket socket = new Socket("localhost", 8080);

            // Enviar la frase SOLICITAR_DESPEGUE al servidor
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println("SOLICITAR_ATERRIZAJE");

            // Leer la solicitud del servidor
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String solicitud = in.readLine();
            System.out.println(solicitud);
            // Verificar si el servidor solicitó el código del vuelo
            if (solicitud != null && solicitud.equals("SOLICITAR_CODIGO")) {
                // Obtener el código del vuelo ingresado por el usuario
                String codigoVuelo = txfCodigo.getText();
                // Enviar el código del vuelo al servidor
                out.println(codigoVuelo);
                // Leer la respuesta del servidor
                String respuesta = in.readLine();
                System.out.println(respuesta);
                if (respuesta.equals("AUTORIZED")) {
                    new Thread(() -> {
                        try {
                            String res = in.readLine();
                            System.out.println(respuesta);
                            if (res.equalsIgnoreCase("READY")) {
                                txfEstado.setText("En Pista");
                                txfEstadoActual.setText("Aterrizaje realizado con éxito...");
                            } else {
                                showErrorMessageDialog("No se pudo aterrizar");
                            }
                            socket.close();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }).start();
                }

            } else {
                showErrorMessageDialog("No se pudo terminar la comunicación con el servidor");
            }
        } catch (IOException e) {
            showErrorMessageDialog("Ocurrió un error, intente de nuevo");
        }
    }

    @FXML
    private void solicitarPuente() {
        try {
            txfAccion.setText("Solicitando puente");
            // Establecer conexión con el servidor
            Socket socket = new Socket("localhost", 8080);

            // Enviar la frase SOLICITAR_DESPEGUE al servidor
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println("SOLICITAR_PUENTE");

            // Leer la solicitud del servidor
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String solicitud = in.readLine();
            System.out.println(solicitud);
            // Verificar si el servidor solicitó el código del vuelo
            if (solicitud != null && solicitud.equals("SOLICITAR_CODIGO")) {
                // Obtener el código del vuelo ingresado por el usuario
                String codigoVuelo = txfCodigo.getText();
                // Enviar el código del vuelo al servidor
                out.println(codigoVuelo);
                // Leer la respuesta del servidor
                String respuesta = in.readLine();
                System.out.println(respuesta);
                // Cerrar conexión
                socket.close();

                // Actualizar los campos de texto con la respuesta del servidor
                if (respuesta.equals("AUTORIZED")) {
                    txfEstado.setText("En Puente");
                    txfEstadoActual.setText("Autorizado, Moviendose a Puente.");
                } else if (respuesta.equals("UNAUTORIZED")) {
                    txfEstadoActual.setText("No fue autorizado moverse a Puente");
                } else {
                    showErrorMessageDialog("No se encontró ese vuelo");
                }

            } else {
                showErrorMessageDialog("No se pudo terminar la comunicación con el servidor");
            }
        } catch (IOException e) {
            showErrorMessageDialog("Ocurrió un error, intente de nuevo");
        }
    }

    @FXML
    private void solicitarPista() {
        try {
            txfAccion.setText("Solicitando pista");
            // Establecer conexión con el servidor
            Socket socket = new Socket("localhost", 8080);

            // Enviar la frase SOLICITAR_DESPEGUE al servidor
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println("SOLICITAR_PISTA");

            // Leer la solicitud del servidor
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String solicitud = in.readLine();
            System.out.println(solicitud);
            // Verificar si el servidor solicitó el código del vuelo
            if (solicitud != null && solicitud.equals("SOLICITAR_CODIGO")) {
                // Obtener el código del vuelo ingresado por el usuario
                String codigoVuelo = txfCodigo.getText();
                // Enviar el código del vuelo al servidor
                out.println(codigoVuelo);
                // Leer la respuesta del servidor
                String respuesta = in.readLine();
                System.out.println(respuesta);
                // Cerrar conexión
                socket.close();

                // Actualizar los campos de texto con la respuesta del servidor
                if (respuesta.equals("AUTORIZED")) {
                    txfEstado.setText("En Pista");
                    txfEstadoActual.setText("Moviendose a Pista.");
                } else if (respuesta.equals("UNAUTORIZED")) {
                    txfEstadoActual.setText("No hay pistas disponibles");
                } else {
                    showErrorMessageDialog("No se encontró ese vuelo");
                }

            } else {
                showErrorMessageDialog("No se pudo terminar la comunicación con el servidor");
            }
        } catch (IOException e) {
            showErrorMessageDialog("Ocurrió un error, intente de nuevo");
        }
    }

    private void actualizarEstadoBotones(boolean isDisable) {
        btnAterrizar.setDisable(isDisable);
        btnPuente.setDisable(isDisable);
        btnPista.setDisable(isDisable);
        btnDespegar.setDisable(isDisable);
    }
    private void showErrorMessageDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Método de inicialización del controlador
    public void init() {
        actualizarEstadoBotones(true);
        // Registra el evento de clic en el botón "Consultar"
        btnConsultar.setOnAction(event -> {
            if(!esperandoDespegue) {
                consultarInformacion();
            }else{
                showErrorMessageDialog("No puede hacerlo mientras espera el despegue");
            }
        });
        btnDespegar.setOnAction(event -> {
            if (txfEstado.getText().equalsIgnoreCase("En Pista") && !esperandoDespegue) {
                solicitarDespegue();
            }else if(esperandoDespegue){
                showErrorMessageDialog("No puede hacerlo mientras espera el despegue");
            } else {
                showErrorMessageDialog("No está en pista, primero solicítelo");
            }
        });

        btnAterrizar.setOnAction(event -> {
            if (txfEstado.getText().equalsIgnoreCase("En Vuelo") && !esperandoDespegue) {
                solicitarAterrizar();
            }else if(esperandoDespegue){
                showErrorMessageDialog("No puede hacerlo mientras espera el despegue");
            } else {
                showErrorMessageDialog("Solo se puede aterriza si está en vuelo, primero debe despegar");
            }
        });

        btnPuente.setOnAction(event -> {
            if (txfEstado.getText().equalsIgnoreCase("En Pista") && !esperandoDespegue) {
                solicitarPuente();
            }else if(esperandoDespegue){
                showErrorMessageDialog("No puede hacerlo mientras espera el despegue");
            } else {
                showErrorMessageDialog("Solo se puede mover a puente si está en Pista, primero debe estarlo");
            }
        });

        btnPista.setOnAction(event -> {
            if(esperandoDespegue){
                showErrorMessageDialog("No puede hacerlo mientras espera el despegue");
            }else{
                solicitarPista();
            }
        });
    }
}