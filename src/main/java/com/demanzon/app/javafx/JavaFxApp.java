package com.demanzon.app.javafx;

import java.util.regex.Pattern;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.demanzon.app.AppApplication;
import com.demanzon.app.DTO.DTOActualizacion;
import com.demanzon.app.DTO.DTOVersion;
import com.demanzon.app.DTO.DTOPing;
import com.demanzon.app.service.ServicioAPI;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.BorderPane;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.layout.Region;
import javafx.stage.Modality;

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
        Button botonAjustes = new Button("Ajustes");
        Region separador = new Region();
        HBox.setHgrow(separador, Priority.ALWAYS);
        HBox encabezado = new HBox(15, imagenView, tituloJapones);
        encabezado.getChildren().addAll(separador, botonAjustes);

        Label labelVersionAC = new Label();
        Label labelVersionACP = new Label();
        Label labelMensajeActualizacionAC = new Label();
        Label labelMensajeActualizacionACP = new Label();
        Label labelIconoActualizacionAC = new Label();
        Label labelIconoActualizacionACP = new Label();
        Label conexionExitosa = new Label();
        Label entornoAPI = new Label();
        Button btnObtenerVersionAC = new Button("Mostrar Versión Calendario de Anime");
        Button btnObtenerVersionACP = new Button("Mostrar Versión Calendario de Anime pro");
        Button btnActualizarVersionAC = new Button("Actualizar Versión Calendario de Anime");
        Button btnActualizarVersionACP = new Button("Actualizar Versión Calendario de Anime pro");
        Button btnIncrementarVersionAC = new Button("Incrementar +1 versión Anime Calendar");
        Button btnIncrementarVersionACP = new Button("Incrementar +1 versión Anime Calendar pro");
        Button btnPing = new Button("Comprobar conexión con la API");
        TextField txtActualizarVersionAC = new TextField();
        TextField txtActualizarVersionACP = new TextField();

        HBox filaV1 = new HBox(10, btnObtenerVersionAC, labelVersionAC);
        HBox filaV2 = new HBox(10, btnObtenerVersionACP, labelVersionACP);
        HBox filaActualizarV1 = new HBox(10, btnActualizarVersionAC, txtActualizarVersionAC,
                labelMensajeActualizacionAC, labelIconoActualizacionAC);
        HBox filaActualizarV2 = new HBox(10, btnActualizarVersionACP, txtActualizarVersionACP,
                labelMensajeActualizacionACP, labelIconoActualizacionACP);
        HBox filaIncrementarV1 = new HBox(10, btnIncrementarVersionAC);
        HBox filaIncrementarV2 = new HBox(10, btnIncrementarVersionACP);

        VBox labelsPing = new VBox(5, conexionExitosa, entornoAPI);
        labelsPing.setAlignment(Pos.CENTER_RIGHT);
        labelsPing.setMaxWidth(Double.MAX_VALUE);

        HBox filaPing = new HBox(10, btnPing, labelsPing);
        HBox.setHgrow(labelsPing, Priority.ALWAYS);

        VBox seccionAC = new VBox(10, filaV1, filaActualizarV1, filaIncrementarV1);
        VBox seccionACP = new VBox(10, filaV2, filaActualizarV2, filaIncrementarV2);
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

        labelVersionAC.setStyle("-fx-font-weight: bold;");
        labelVersionACP.setStyle("-fx-font-weight: bold;");
        labelMensajeActualizacionAC.setStyle("-fx-font-weight: bold;");
        labelMensajeActualizacionACP.setStyle("-fx-font-weight: bold;");
        labelIconoActualizacionAC.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");
        labelIconoActualizacionACP.setStyle("-fx-font-size: 18; -fx-font-weight: bold;");
        conexionExitosa.setStyle("-fx-font-weight: bold;");
        entornoAPI.setStyle("-fx-font-weight: bold;");

        btnObtenerVersionAC.setStyle("-fx-base: #D8BFD8; -fx-font-size: 11;");
        btnObtenerVersionACP.setStyle("-fx-base: #ADD8E6; -fx-font-size: 11;");
        btnActualizarVersionAC.setStyle("-fx-base: #D8BFD8; -fx-font-size: 11;");
        btnActualizarVersionACP.setStyle("-fx-base: #ADD8E6; -fx-font-size: 11;");
        btnIncrementarVersionAC.setStyle("-fx-base: #D8BFD8; -fx-font-size: 11;");
        btnIncrementarVersionACP.setStyle("-fx-base: #ADD8E6; -fx-font-size: 11;");
        btnPing.setStyle("-fx-base: #c9fac9d3; -fx-font-size: 11;");

        txtActualizarVersionAC.setPromptText("Nueva versión normal");
        txtActualizarVersionACP.setPromptText("Nueva versión pro");

        filaV1.setAlignment(Pos.CENTER_LEFT);
        filaV2.setAlignment(Pos.CENTER_LEFT);
        filaActualizarV1.setAlignment(Pos.CENTER_LEFT);
        filaActualizarV2.setAlignment(Pos.CENTER_LEFT);
        filaIncrementarV1.setAlignment(Pos.CENTER_LEFT);
        filaIncrementarV2.setAlignment(Pos.CENTER_LEFT);
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
                    return servicioAPI.obtenerVersionPro();
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

        btnPing.setOnAction(e -> {

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
        });

        btnActualizarVersionAC.setOnAction(e -> {

            String nuevaVersion = txtActualizarVersionAC.getText();
            Pattern versionRegex = Pattern.compile("^V\\.\\d+\\.\\d+\\.\\d+$");

            if (nuevaVersion.isBlank()) {
                labelMensajeActualizacionAC.setText("Introduce una versión");
                actualizarIconoEstado(labelIconoActualizacionAC, false);
                mostrarPopupError("Debes introducir una versión para actualizar Anime Calendar");
                return;
            }

            if (!versionRegex.matcher(nuevaVersion).matches()) {
                labelMensajeActualizacionAC.setText("Formato de versión inválido");
                actualizarIconoEstado(labelIconoActualizacionAC, false);
                mostrarPopupError("El formato debe ser V.x.y.z (por ejemplo V.1.2.3). ");
                return;
            }

            labelMensajeActualizacionAC.setText("Cargando...");
            actualizarIconoEstado(labelIconoActualizacionAC, null);

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
                    actualizarIconoEstado(labelIconoActualizacionAC, true);
                } else {
                    labelMensajeActualizacionAC.setText("La actualización falló");
                    actualizarIconoEstado(labelIconoActualizacionAC, false);
                    mostrarPopupError("La actualización de Anime Calendar no se pudo completar");
                }
            });

            task.setOnFailed(event -> {
                labelMensajeActualizacionAC.setText("La actualización falló");
                actualizarIconoEstado(labelIconoActualizacionAC, false);
                mostrarPopupError("Ocurrió un error al actualizar Anime Calendar");
            });

            new Thread(task).start();
        });

        btnActualizarVersionACP.setOnAction(e -> {

            String nuevaVersion = txtActualizarVersionACP.getText();
            Pattern versionRegex = Pattern.compile("^V\\.\\d+\\.\\d+\\.\\d+$");

            if (nuevaVersion.isBlank()) {
                labelMensajeActualizacionACP.setText("Introduce una versión");
                actualizarIconoEstado(labelIconoActualizacionACP, false);
                mostrarPopupError("Debes introducir una versión para actualizar Anime Calendar pro");
                return;
            }

            if (!versionRegex.matcher(nuevaVersion).matches()) {
                labelMensajeActualizacionACP.setText("Formato de versión inválido");
                actualizarIconoEstado(labelIconoActualizacionACP, false);
                mostrarPopupError("El formato debe ser V.x.y.z (por ejemplo V.1.2.3). ");
                return;
            }

            labelMensajeActualizacionACP.setText("Cargando...");
            actualizarIconoEstado(labelIconoActualizacionACP, null);

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
                    actualizarIconoEstado(labelIconoActualizacionACP, true);
                } else {
                    labelMensajeActualizacionACP.setText("La actualización falló");
                    actualizarIconoEstado(labelIconoActualizacionACP, false);
                    mostrarPopupError("La actualización de Anime Calendar pro no se pudo completar");
                }
            });

            task.setOnFailed(event -> {
                labelMensajeActualizacionACP.setText("La actualización falló");
                actualizarIconoEstado(labelIconoActualizacionACP, false);
                mostrarPopupError("Ocurrió un error al actualizar Anime Calendar pro");
            });

            new Thread(task).start();
        });

        btnIncrementarVersionAC.setOnAction(e -> {
            labelMensajeActualizacionAC.setText("Cargando...");
            actualizarIconoEstado(labelIconoActualizacionAC, null);

            Task<DTOActualizacion> task = new Task<>() {
                @Override
                protected DTOActualizacion call() {
                    DTOVersion versionActual = servicioAPI.obtenerVersion();
                    String versionIncrementada = incrementarUltimoNumeroVersion(versionActual.getVersion());

                    if (versionIncrementada == null) {
                        throw new IllegalArgumentException("Formato de versión inválido");
                    }

                    return servicioAPI.obtenerActualizacion(versionIncrementada);
                }
            };

            task.setOnSucceeded(event -> {
                DTOActualizacion resultado = task.getValue();

                if (resultado.isActualizacionExitosa()) {
                    labelMensajeActualizacionAC.setText("Actualización realizada correctamente");
                    actualizarIconoEstado(labelIconoActualizacionAC, true);
                } else {
                    labelMensajeActualizacionAC.setText("La actualización falló");
                    actualizarIconoEstado(labelIconoActualizacionAC, false);
                    mostrarPopupError("La actualización incremental de Anime Calendar no se pudo completar");
                }
            });

            task.setOnFailed(event -> {
                Throwable error = task.getException();

                if (error instanceof IllegalArgumentException) {
                    labelMensajeActualizacionAC.setText("Formato de versión inválido");
                    actualizarIconoEstado(labelIconoActualizacionAC, false);
                    mostrarPopupError("El formato de la versión actual no es válido para incrementar Anime Calendar");
                } else {
                    labelMensajeActualizacionAC.setText("La actualización falló");
                    actualizarIconoEstado(labelIconoActualizacionAC, false);
                    mostrarPopupError("Ocurrió un error al realizar la actualización incremental de Anime Calendar");
                }
            });

            new Thread(task).start();
        });

        btnIncrementarVersionACP.setOnAction(e -> {
            labelMensajeActualizacionACP.setText("Cargando...");
            actualizarIconoEstado(labelIconoActualizacionACP, null);

            Task<DTOActualizacion> task = new Task<>() {
                @Override
                protected DTOActualizacion call() {
                    DTOVersion versionActual = servicioAPI.obtenerVersionPro();
                    String versionIncrementada = incrementarUltimoNumeroVersion(versionActual.getVersion());

                    if (versionIncrementada == null) {
                        throw new IllegalArgumentException("Formato de versión inválido");
                    }

                    return servicioAPI.obtenerActualizacionPro(versionIncrementada);
                }
            };

            task.setOnSucceeded(event -> {
                DTOActualizacion resultado = task.getValue();

                if (resultado.isActualizacionExitosa()) {
                    labelMensajeActualizacionACP.setText("Actualización realizada correctamente");
                    actualizarIconoEstado(labelIconoActualizacionACP, true);
                } else {
                    labelMensajeActualizacionACP.setText("La actualización falló");
                    actualizarIconoEstado(labelIconoActualizacionACP, false);
                    mostrarPopupError("La actualización incremental de Anime Calendar pro no se pudo completar");
                }
            });

            task.setOnFailed(event -> {
                Throwable error = task.getException();

                if (error instanceof IllegalArgumentException) {
                    labelMensajeActualizacionACP.setText("Formato de versión inválido");
                    actualizarIconoEstado(labelIconoActualizacionACP, false);
                    mostrarPopupError(
                            "El formato de la versión actual no es válido para incrementar Anime Calendar pro");
                } else {
                    labelMensajeActualizacionACP.setText("La actualización falló");
                    actualizarIconoEstado(labelIconoActualizacionACP, false);
                    mostrarPopupError(
                            "Ocurrió un error al realizar la actualización incremental de Anime Calendar pro");
                }
            });

            new Thread(task).start();
        });

        botonAjustes.setOnAction(eventoAjustes -> {
            Stage dialogo = new Stage();
            dialogo.initOwner(stage);
            dialogo.initModality(Modality.APPLICATION_MODAL);
            dialogo.setTitle("Ajustes");

            Label etiqueta = new Label("URL de la API:");
            TextField campoUrl = new TextField();
            String actual = servicioAPI.obtenerUrlBase();
            if (actual != null) campoUrl.setText(actual);

            Button botonGuardar = new Button("Guardar");
            Button botonCancelar = new Button("Cancelar");

            HBox cajaBotones = new HBox(10, botonGuardar, botonCancelar);
            cajaBotones.setAlignment(Pos.CENTER_RIGHT);

            VBox caja = new VBox(10, etiqueta, campoUrl, cajaBotones);
            caja.setPadding(new Insets(12));

            botonGuardar.setOnAction(eventoGuardar -> {
                String nueva = campoUrl.getText();
                if (nueva != null && !nueva.isBlank()) {
                    servicioAPI.establecerUrlBase(nueva.trim());
                    servicioAPI.guardarUrlBase(nueva.trim());
                }
                dialogo.close();
            });

            botonCancelar.setOnAction(eventoCancelar -> dialogo.close());

            Scene escenaDialogo = new Scene(caja, 480, 140);
            dialogo.setScene(escenaDialogo);
            dialogo.showAndWait();
        });

        stage.setTitle("Gestor de Versiones");
        stage.setScene(scene);
        stage.show();

        btnPing.fire();
    }

    @Override
    public void stop() {
        context.close();
        Platform.exit();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private String incrementarUltimoNumeroVersion(String version) {
        Pattern versionRegex = Pattern.compile("^V\\.\\d+\\.\\d+\\.\\d+$");

        if (version == null || !versionRegex.matcher(version).matches()) {
            return null;
        }

        String[] partes = version.substring(2).split("\\.");
        int patch = Integer.parseInt(partes[2]) + 1;

        return "V." + partes[0] + "." + partes[1] + "." + patch;
    }

    private void actualizarIconoEstado(Label labelIcono, Boolean exito) {
        if (exito == null) {
            labelIcono.setText("");
            return;
        }

        if (exito) {
            labelIcono.setText("✓");
            labelIcono.setStyle("-fx-font-size: 18; -fx-font-weight: bold; -fx-text-fill: green;");
        } else {
            labelIcono.setText("✗");
            labelIcono.setStyle("-fx-font-size: 18; -fx-font-weight: bold; -fx-text-fill: red;");
        }
    }

    private void mostrarPopupError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("No se pudo completar la actualización");
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}