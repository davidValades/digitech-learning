import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        //variables para la ip (la ip de la maquina virtual que creemos.
        String ipServidor = "127.0.0.1";
        int puerto = 5000; //puerto que tenemos en la clase servidor

        try {
            System.out.println("Iniciando conexion al servidor en " +ipServidor+ ":" +puerto+ "...");

            Socket socket = new Socket(ipServidor, puerto); //instanciamos el socket con ip y servidor
            System.out.println("¡Conectado al servidor!");

            // canales de comunicacion:
            PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Hilo abierto con el bucle para que siempre este recibiendo:
            Thread hiloAbierto = new Thread(() -> {
                try {
                    String mensajeServidor;
                    while ((mensajeServidor = entrada.readLine()) != null) {
                        System.out.println("Servidor dice: " + mensajeServidor);
                    }
                }
                catch (IOException e) {
                        System.out.println("desconectado del servidor");
                }
            });
            hiloAbierto.start();

            // Hilo princiapal, lee el teclado y lo envia al servidor
            Scanner teclado = new Scanner(System.in);
            while (true) {
                String miMensaje = teclado.nextLine();
                salida.println(miMensaje);
            }

        } catch (IOException e) {
            System.out.println("Error al conectar: " + e.getMessage());
        }
    }
}
