package com.demanzon.app.javafx;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.demanzon.app.DTO.DTOActualizacion;
import com.demanzon.app.DTO.DTOVersion;
import com.demanzon.app.controller.TestControlador;
import com.demanzon.app.service.ServicioAPI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.stage.Stage;

public class JavaFxApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {

        TextArea area = new TextArea();
        area.setEditable(false);
        area.setPrefHeight(200);

        Button btnMostrarV1 = new Button("Mostrar Versión Calendario de Anime");
        Button btnMostrarV2 = new Button("Mostrar Versión Calendario de Anime premium");

        Button btnActualizarV1 = new Button("Actualizar Versión Calendario de Anime");
        Button btnActualizarV2 = new Button("Actualizar Versión Calendario de Anime premium");

        btnMostrarV1.setOnAction(e -> area.setText("Mostrando versión 1..."));
        btnMostrarV2.setOnAction(e -> area.setText("Mostrando versión 2..."));
        btnActualizarV1.setOnAction(e -> area.setText("Actualizando versión 1..."));
        btnActualizarV2.setOnAction(e -> area.setText("Actualizando versión 2..."));

        VBox root = new VBox(15,
                btnMostrarV1,
                btnMostrarV2,
                btnActualizarV1,
                btnActualizarV2,
                area);

        root.setPadding(new Insets(20));

        Scene scene = new Scene(root, 400, 350);

        stage.setTitle("Gestor de Versiones");
        stage.setScene(scene);
        stage.show();
    }
}