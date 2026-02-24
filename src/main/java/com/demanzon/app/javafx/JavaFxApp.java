package com.demanzon.app.javafx;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.demanzon.app.AppApplication;
import com.demanzon.app.DTO.DTOActualizacion;
import com.demanzon.app.DTO.DTOVersion;
import com.demanzon.app.service.ServicioAPI;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import javafx.stage.Stage;

public class JavaFxApp extends Application {

    private ConfigurableApplicationContext context;
    private ServicioAPI servicioAPI;

    @Override
    public void init() {
        context = new SpringApplicationBuilder(AppApplication.class).run();
        servicioAPI = context.getBean(ServicioAPI.class);
    }

    @Override
    public void start(Stage stage) {

        Label labelVersionAC = new Label();
        Label labelVersionACP = new Label();

        labelVersionAC.setStyle("-fx-font-weight: bold;");
        labelVersionACP.setStyle("-fx-font-weight: bold;");

        Button btnObtenerVersionAC = new Button("Mostrar Versión Calendario de Anime");
        Button btnObtenerVersionACP = new Button("Mostrar Versión Calendario de Anime premium");

        Button btnActualizarVersionAC = new Button("Actualizar Versión Calendario de Anime");
        Button btnActualizarVersionACP = new Button("Actualizar Versión Calendario de Anime premium");

        TextField txtActualizarVersionAC = new TextField();
        txtActualizarVersionAC.setPromptText("Nueva versión normal");

        TextField txtActualizarVersionACP = new TextField();
        txtActualizarVersionACP.setPromptText("Nueva versión premium");

        btnObtenerVersionAC.setOnAction(e -> {

            labelVersionAC.setText("Cargando...");

            Task<DTOVersion> task = new Task<>() {
                @Override
                protected DTOVersion call() {
                    return servicioAPI.obtenerVersion();
                }
            };

            task.setOnSucceeded(event -> {
                labelVersionAC.setText(task.getValue().getVersion());
            });

            task.setOnFailed(event -> {
                labelVersionAC.setText("Error al conectar");
            });

            new Thread(task).start();
        });

        btnObtenerVersionACP.setOnAction(e -> {

            labelVersionACP.setText("Cargando...");

            Task<DTOVersion> task = new Task<>() {
                @Override
                protected DTOVersion call() {
                    return servicioAPI.obtenerVersionPremium();
                }
            };

            task.setOnSucceeded(event -> {
                labelVersionACP.setText(task.getValue().getVersion());
            });

            task.setOnFailed(event -> {
                labelVersionACP.setText("Error al conectar");
            });

            new Thread(task).start();
        });

        btnActualizarVersionAC.setOnAction(e -> {

            String nuevaVersion = txtActualizarVersionAC.getText();

            if (nuevaVersion.isBlank()) {
                labelVersionAC.setText("Introduce una versión");
                return;
            }

            labelVersionAC.setText("Actualizando...");

            Task<DTOActualizacion> task = new Task<>() {
                @Override
                protected DTOActualizacion call() {
                    return servicioAPI.obtenerActualizacion(nuevaVersion);
                }
            };

            task.setOnSucceeded(event -> {

                DTOActualizacion resultado = task.getValue();

                if (resultado.isActualizacionExitosa()) {
                    labelVersionAC.setText("Actualización realizada correctamente");
                } else {
                    labelVersionAC.setText("La actualización falló");
                }
            });

            new Thread(task).start();
        });

        btnActualizarVersionACP.setOnAction(e -> {

            String nuevaVersion = txtActualizarVersionACP.getText();

            if (nuevaVersion.isBlank()) {
                labelVersionACP.setText("Introduce una versión");
                return;
            }

            labelVersionACP.setText("Actualizando...");

            Task<DTOActualizacion> task = new Task<>() {
                @Override
                protected DTOActualizacion call() {
                    return servicioAPI.obtenerActualizacionPremium(nuevaVersion);
                }
            };

            task.setOnSucceeded(event -> {

                DTOActualizacion resultado = task.getValue();

                if (resultado.isActualizacionExitosa()) {
                    labelVersionACP.setText("Actualización realizada correctamente");
                } else {
                    labelVersionACP.setText("La actualización falló");
                }
            });

            new Thread(task).start();
        });

        HBox filaV1 = new HBox(10, btnObtenerVersionAC, labelVersionAC);
        HBox filaV2 = new HBox(10, btnObtenerVersionACP, labelVersionACP);
        HBox filaActualizarV1 = new HBox(10, btnActualizarVersionAC, txtActualizarVersionAC);
        HBox filaActualizarV2 = new HBox(10, btnActualizarVersionACP, txtActualizarVersionACP);

        VBox root = new VBox(15,
                filaV1,
                filaV2,
                filaActualizarV1,
                filaActualizarV2);

        root.setPadding(new Insets(20));

        Scene scene = new Scene(root, 500, 200);

        stage.setTitle("Gestor de Versiones");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() {
        context.close();
        Platform.exit();
    }

    public static void main(String[] args) {
        launch(args);
    }
}