import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClienteVisual extends Application {

    private PrintWriter salida;
    private BufferedReader entrada;
    private TextArea areaMensajes;
    private TextField campoTexto;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage escenarioPrincipal) {
        // creamos el area donde se veran los mensajes
        areaMensajes = new TextArea();
        areaMensajes.setEditable(false);

        // creamos el campo para escribir el texto
        campoTexto = new TextField();
        campoTexto.setPrefWidth(300);

        // creamos el boton
        Button botonEnviar = new Button("Enviar");

        // le damos la accion al boton y a la tecla enter
        botonEnviar.setOnAction(e -> enviarMensaje());
        campoTexto.setOnAction(e -> enviarMensaje());

        // juntamos todo en la ventana
        HBox barraInferior = new HBox(10, campoTexto, botonEnviar);
        BorderPane layout = new BorderPane();
        layout.setCenter(areaMensajes);
        layout.setBottom(barraInferior);

        Scene escena = new Scene(layout, 400, 300);
        escenarioPrincipal.setTitle("Cliente");
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.show();

        // llamamos al metodo para conectar
        conectarAlServidor();
    }

    private void conectarAlServidor() {
        // Hilo abierto con el bucle para que siempre este recibiendo:
        Thread hiloAbierto = new Thread(() -> {
            //variables para la ip (la ip de la maquina virtual que creemos.
            String ipServidor = "127.0.0.1";
            int puerto = 5000; //puerto que tenemos en la clase servidor

            try {
                Socket socket = new Socket(ipServidor, puerto); //instanciamos el socket con ip y servidor

                Platform.runLater(() -> areaMensajes.appendText("¡Conectado al servidor!\n"));

                // canales de comunicacion:
                salida = new PrintWriter(socket.getOutputStream(), true);
                entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                String mensajeServidor;
                while ((mensajeServidor = entrada.readLine()) != null) {
                    String msj = mensajeServidor;
                    // actualizamos la interfaz visual sin romper el hilo
                    Platform.runLater(() -> areaMensajes.appendText("Servidor dice: " + msj + "\n"));
                }
            } catch (IOException e) {
                Platform.runLater(() -> areaMensajes.appendText("Error al conectar o desconectado del servidor.\n"));
            }
        });
        hiloAbierto.start();
    }

    private void enviarMensaje() {
        String miMensaje = campoTexto.getText();
        if (!miMensaje.isEmpty() && salida != null) {
            salida.println(miMensaje);
            campoTexto.clear(); // limpiamos la caja despues de enviar
        }
    }
}