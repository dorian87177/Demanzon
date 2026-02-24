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
import javafx.scene.layout.BorderPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

        Image imagenKawai = new Image(getClass().getResourceAsStream("/images/chicaKawai.jpg"));
        ImageView imagenView = new ImageView(imagenKawai);
        Label tituloJapones = new Label("アニメカレンダー");
        HBox encabezado = new HBox(15, imagenView, tituloJapones);

        Label labelVersionAC = new Label();
        Label labelVersionACP = new Label();
        Button btnObtenerVersionAC = new Button("Mostrar Versión Calendario de Anime");
        Button btnObtenerVersionACP = new Button("Mostrar Versión Calendario de Anime premium");
        Button btnActualizarVersionAC = new Button("Actualizar Versión Calendario de Anime");
        Button btnActualizarVersionACP = new Button("Actualizar Versión Calendario de Anime premium");
        TextField txtActualizarVersionAC = new TextField();
        TextField txtActualizarVersionACP = new TextField();

        HBox filaV1 = new HBox(10, btnObtenerVersionAC, labelVersionAC);
        HBox filaV2 = new HBox(10, btnObtenerVersionACP, labelVersionACP);
        HBox filaActualizarV1 = new HBox(10, btnActualizarVersionAC, txtActualizarVersionAC);
        HBox filaActualizarV2 = new HBox(10, btnActualizarVersionACP, txtActualizarVersionACP);

        VBox seccionAC = new VBox(10, filaV1, filaActualizarV1);
        VBox seccionACP = new VBox(10, filaV2, filaActualizarV2);
        VBox contenedor = new VBox(15, seccionAC, seccionACP);
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 475, 375);

        imagenView.setFitHeight(120);
        imagenView.setFitWidth(120);
        imagenView.setPreserveRatio(true);

        tituloJapones.setStyle("-fx-font-size: 24; -fx-font-weight: bold; -fx-text-fill: #000000;");

        encabezado.setAlignment(Pos.CENTER_LEFT);
        encabezado.setPadding(new Insets(15));
        encabezado.setStyle("-fx-background-color: #FFE5F0;");

        labelVersionAC.setStyle("-fx-font-weight: bold;");
        labelVersionACP.setStyle("-fx-font-weight: bold;");

        btnObtenerVersionAC.setStyle("-fx-base: #D8BFD8; -fx-font-size: 11;");
        btnObtenerVersionACP.setStyle("-fx-base: #ADD8E6; -fx-font-size: 11;");
        btnActualizarVersionAC.setStyle("-fx-base: #D8BFD8; -fx-font-size: 11;");
        btnActualizarVersionACP.setStyle("-fx-base: #ADD8E6; -fx-font-size: 11;");

        txtActualizarVersionAC.setPromptText("Nueva versión normal");
        txtActualizarVersionACP.setPromptText("Nueva versión premium");

        filaV1.setAlignment(Pos.CENTER_LEFT);
        filaV2.setAlignment(Pos.CENTER_LEFT);
        filaActualizarV1.setAlignment(Pos.CENTER_LEFT);
        filaActualizarV2.setAlignment(Pos.CENTER_LEFT);

        seccionAC.setPadding(new Insets(12));
        seccionAC.setStyle("-fx-background-color: #F5E6F5;");

        seccionACP.setPadding(new Insets(12));
        seccionACP.setStyle("-fx-background-color: #E5F0FF;");

        contenedor.setPadding(new Insets(15));
        contenedor.setStyle("-fx-background-color: #FFF0F7;");

        root.setTop(encabezado);
        root.setCenter(contenedor);
        root.setStyle("-fx-background-color: #FFF0F7;");

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