package com.example.proyectoprogra2maeba.presentation.client;

import com.example.proyectoprogra2maeba.logic.ControllerCliente;
import com.example.proyectoprogra2maeba.logic.ServidorController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor extends Application {

    public static void main(String[] args) throws IOException {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/serverView.fxml"));
        Parent root = loader.load();

        ServidorController controller = loader.getController();
        controller.initView();

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
