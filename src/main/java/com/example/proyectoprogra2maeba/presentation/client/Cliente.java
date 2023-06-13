package com.example.proyectoprogra2maeba.presentation.client;

import com.example.proyectoprogra2maeba.logic.ControllerCliente;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Cliente extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/clientview.fxml"));
        Parent root = loader.load();

        ControllerCliente controller = loader.getController();
        controller.init();

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Crear y mostrar una segunda instancia del cliente
        Stage secondStage = new Stage();

        FXMLLoader secondLoader = new FXMLLoader();
        secondLoader.setLocation(getClass().getResource("/clientview.fxml"));
        Parent secondRoot = secondLoader.load();

        ControllerCliente secondController = secondLoader.getController();
        secondController.init();

        Scene secondScene = new Scene(secondRoot);
        secondStage.setScene(secondScene);
        secondStage.show();
    }
}