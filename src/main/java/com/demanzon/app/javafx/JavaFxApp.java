package com.demanzon.app.javafx;

import java.util.regex.Pattern;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.demanzon.app.AppApplication;
import com.demanzon.app.DTO.DTOActualizacion;
import com.demanzon.app.DTO.DTOPing;
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

        Image imagenKawai = new Image(getClass().getResourceAsStream("/images/anime_calendar_logo.png"));
        ImageView imagenView = new ImageView(imagenKawai);
        Label tituloJapones = new Label("アニメカレンダー");
        HBox encabezado = new HBox(15, imagenView, tituloJapones);

        Label conexionExitosa = new Label();
        Label entornoAPI = new Label();
        Label labelMensajeActualizacionAC = new Label();
        Label labelMensajeActualizacionACP = new Label();
        Button btnObtenerVersionAC = new Button("Mostrar Versión Calendario de Anime");
        Button btnObtenerVersionACP = new Button("Mostrar Versión Calendario de Anime pro");
        Button btnActualizarVersionAC = new Button("Actualizar Versión Calendario de Anime");
        Button btnActualizarVersionACP = new Button("Actualizar Versión Calendario de Anime premium");
        Button btnPing = new Button("Comprobar conexión con la API");
        TextField txtActualizarVersionAC = new TextField();
        TextField txtActualizarVersionACP = new TextField();

        HBox filaV1 = new HBox(10, btnObtenerVersionAC, labelMensajeActualizacionAC);
        HBox filaV2 = new HBox(10, btnObtenerVersionACP, labelMensajeActualizacionACP);
        HBox filaActualizarV1 = new HBox(10, btnActualizarVersionAC, txtActualizarVersionAC, labelMensajeActualizacionAC);
        HBox filaActualizarV2 = new HBox(10, btnActualizarVersionACP, txtActualizarVersionACP, labelMensajeActualizacionACP);
        VBox labelsPing = new VBox(5, conexionExitosa, entornoAPI);
        HBox filaPing = new HBox(10, btnPing, labelsPing);

        VBox seccionAC = new VBox(10, filaV1, filaActualizarV1);
        VBox seccionACP = new VBox(10, filaV2, filaActualizarV2);
        VBox seccionPing = new VBox(10, filaPing);
        VBox contenedor = new VBox(15, seccionAC, seccionACP, seccionPing);
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 675, 500);

        imagenView.setFitHeight(120);
        imagenView.setFitWidth(120);
        imagenView.setPreserveRatio(true);

        tituloJapones.setStyle("-fx-font-size: 24; -fx-font-weight: bold; -fx-text-fill: #000000;");

        encabezado.setAlignment(Pos.CENTER_LEFT);
        encabezado.setPadding(new Insets(15));
        encabezado.setStyle("-fx-background-color: #FFE5F0;");

        conexionExitosa.setStyle("-fx-font-weight: bold;");
        entornoAPI.setStyle("-fx-font-weight: bold;");
        labelMensajeActualizacionAC.setStyle("-fx-font-weight: bold;");
        labelMensajeActualizacionACP.setStyle("-fx-font-weight: bold;");

        btnObtenerVersionAC.setStyle("-fx-base: #D8BFD8; -fx-font-size: 11;");
        btnObtenerVersionACP.setStyle("-fx-base: #ADD8E6; -fx-font-size: 11;");
        btnActualizarVersionAC.setStyle("-fx-base: #D8BFD8; -fx-font-size: 11;");
        btnActualizarVersionACP.setStyle("-fx-base: #ADD8E6; -fx-font-size: 11;");
        btnPing.setStyle("-fx-base: #c9fac9d3; -fx-font-size: 11;");

        txtActualizarVersionAC.setPromptText("Nueva versión normal");
        txtActualizarVersionACP.setPromptText("Nueva versión pro");

        filaV1.setAlignment(Pos.CENTER_LEFT);
        filaV2.setAlignment(Pos.CENTER_LEFT);
        filaActualizarV1.setAlignment(Pos.CENTER_LEFT);
        filaActualizarV2.setAlignment(Pos.CENTER_LEFT);
        filaPing.setAlignment(Pos.CENTER_LEFT);

        seccionAC.setPadding(new Insets(12));
        seccionAC.setStyle("-fx-background-color: #F5E6F5;");

        seccionACP.setPadding(new Insets(12));
        seccionACP.setStyle("-fx-background-color: #E5F0FF;");

        seccionPing.setPadding(new Insets(12));
        seccionPing.setStyle("-fx-background-color: #f0ffd8ea;");

        contenedor.setPadding(new Insets(15));
        contenedor.setStyle("-fx-background-color: #FFF0F7;");

        root.setTop(encabezado);
        root.setCenter(contenedor);
        root.setStyle("-fx-background-color: #FFF0F7;");

        btnObtenerVersionAC.setOnAction(e -> {

            labelMensajeActualizacionAC.setText("Cargando...");

            Task<DTOVersion> task = new Task<>() {
                @Override
                protected DTOVersion call() {
                    return servicioAPI.obtenerVersion();
                }
            };

            task.setOnSucceeded(event -> {
                labelMensajeActualizacionAC.setText(task.getValue().getVersion());
            });

            task.setOnFailed(event -> {
                labelMensajeActualizacionAC.setText("Error al conectar");
            });

            new Thread(task).start();
        });

        btnObtenerVersionACP.setOnAction(e -> {

            labelMensajeActualizacionACP.setText("Cargando...");

            Task<DTOVersion> task = new Task<>() {
                @Override
                protected DTOVersion call() {
                    return servicioAPI.obtenerVersionPro();
                }
            };

            task.setOnSucceeded(event -> {
                labelMensajeActualizacionACP.setText(task.getValue().getVersion());
            });

            task.setOnFailed(event -> {
                labelMensajeActualizacionACP.setText("Error al conectar");
            });

            new Thread(task).start();
        });

        btnActualizarVersionAC.setOnAction(e -> {

            String nuevaVersion = txtActualizarVersionAC.getText();
            Pattern versionRegex = Pattern.compile("^V\\.\\d+\\.\\d+\\.\\d+$");

            if (nuevaVersion.isBlank()) {
                labelMensajeActualizacionAC.setText("Introduce una versión");
                return;
            }

            if (!versionRegex.matcher(nuevaVersion).matches()) {
                labelMensajeActualizacionAC.setText("Formato de versión inválido");
                return;
            }

            labelMensajeActualizacionAC.setText("Cargando...");

            Task<DTOActualizacion> task = new Task<>() {
                @Override
                protected DTOActualizacion call() {
                    return servicioAPI.obtenerActualizacion(nuevaVersion);
                }
            };

            task.setOnSucceeded(event -> {

                DTOActualizacion resultado = task.getValue();

                if (resultado.isActualizacionExitosa()) {
                    labelMensajeActualizacionAC.setText("Actualización realizada correctamente");
                } else {
                    labelMensajeActualizacionAC.setText("La actualización falló");
                }
            });

            task.setOnFailed(event -> {
                labelMensajeActualizacionAC.setText("La actualización falló");
            });

            new Thread(task).start();
        });

        btnActualizarVersionACP.setOnAction(e -> {

            String nuevaVersion = txtActualizarVersionACP.getText();
            Pattern versionRegex = Pattern.compile("^V\\.\\d+\\.\\d+\\.\\d+$");

            if (nuevaVersion.isBlank()) {
                labelMensajeActualizacionACP.setText("Introduce una versión");
                return;
            }

            if (!versionRegex.matcher(nuevaVersion).matches()) {
                labelMensajeActualizacionACP.setText("Formato de versión inválido");
                return;
            }

            labelMensajeActualizacionACP.setText("Cargando...");

            Task<DTOActualizacion> task = new Task<>() {
                @Override
                protected DTOActualizacion call() {
                    return servicioAPI.obtenerActualizacionPro(nuevaVersion);
                }
            };

            task.setOnSucceeded(event -> {

                DTOActualizacion resultado = task.getValue();

                if (resultado.isActualizacionExitosa()) {
                    labelMensajeActualizacionACP.setText("Actualización realizada correctamente");
                } else {
                    labelMensajeActualizacionACP.setText("La actualización falló");
                }
            });

            task.setOnFailed(event -> {
                labelMensajeActualizacionACP.setText("La actualización falló");
            });

            new Thread(task).start();
        });

        btnPing.setOnAction(e -> realizarPing(conexionExitosa, entornoAPI));

        stage.setTitle("Gestor de Versiones");
        stage.setScene(scene);
        stage.show();

        realizarPing(conexionExitosa, entornoAPI);
    }

    private void realizarPing(Label conexionExitosa, Label entornoAPI) {
        conexionExitosa.setText("Comprobando conexión...");
        entornoAPI.setText("");

        Task<DTOPing> task = new Task<>() {
            @Override
            protected DTOPing call() {
                return servicioAPI.obtenerPing();
            }
        };

        task.setOnSucceeded(event -> {
            DTOPing resultado = task.getValue();
            if (resultado.isOk()) {
                conexionExitosa.setText("Conexión exitosa");
                entornoAPI.setText("Entorno: " + resultado.getEntorno());
            } else {
                conexionExitosa.setText("Conexión fallida");
                entornoAPI.setText("");
            }
        });

        task.setOnFailed(event -> {
            conexionExitosa.setText("Error al conectar");
            entornoAPI.setText("");
        });

        new Thread(task).start();
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