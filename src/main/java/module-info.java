module com.example.proyectoprogra2maeba {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.example.proyectoprogra2maeba to javafx.fxml;
    opens com.example.proyectoprogra2maeba.client to javafx.fxml;
    exports com.example.proyectoprogra2maeba.client;
    exports com.example.proyectoprogra2maeba;
}