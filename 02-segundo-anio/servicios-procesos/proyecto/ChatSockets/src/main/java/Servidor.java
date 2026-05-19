import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class Servidor {

    // Lista global para guardar a todos los clientes y poder enviarles mensajes a todos a la vez
    private static Set<PrintWriter> escritores = new HashSet<>();

    public static void main(String[] args) {
        int puerto = 5000; // puerto donde se escucha

        try {
            //iniciamos serv
            ServerSocket servidor = new ServerSocket(puerto); //inicializamos con la variable del puert
            System.out.println("Servidor iniciado correctamente.");

            // bucle que se repite para permitir la espera del cliente
            while (true) {

                System.out.println("Esperando a que un cliente se conecte en el puerto " + puerto + "...");
                Socket cliente = servidor.accept();
                System.out.println("Nuevo cliente conectado desde la IP: " + cliente.getInetAddress() + ".");

                //asignamos el cliente a su propio hilo/trhead par ano bloquear el servidor
                Thread hiloDeAtencion = new Thread(new ManejadorCliente(cliente)); //esta nueva instancia le damos vida mas abajo
                hiloDeAtencion.start();
            }
        } catch (IOException e) {
            System.out.println("Error al inicializar servidor "+ e.getMessage()); //cubrimos el error por si falla.
        }
    }

    // esta nueva instancia le damos vida aqui abajo, implementando Runnable para los hilos
    static class ManejadorCliente implements Runnable {
        private Socket socketCliente;
        private PrintWriter salida;

        // constructor que recibe el socket del cliente que se acaba de conectar
        public ManejadorCliente(Socket socket) {
            this.socketCliente = socket;
        }

        @Override
        public void run() {
            try {
                // canales de comunicacion para leer lo que entra y enviar lo que sale
                BufferedReader entrada = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
                salida = new PrintWriter(socketCliente.getOutputStream(), true);

                // añadimos este cliente a la lista global para que empiece a recibir mensajes
                synchronized (escritores) {
                    escritores.add(salida);
                }

                String mensaje;
                // bucle infinito: mientras el cliente escriba algo, se lo reenviamos a todos
                while ((mensaje = entrada.readLine()) != null) {
                    System.out.println("Recibido en el serv: " + mensaje);

                    // recorremos la lista de clientes conectados y les mandamos el mensaje
                    synchronized (escritores) {
                        for (PrintWriter escritor : escritores) {
                            escritor.println(mensaje);
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Un cliente se ha desconectado abruptamente.");
            } finally {
                // bloque finally para limpiar si el cliente se va o hay error
                if (salida != null) {
                    synchronized (escritores) {
                        escritores.remove(salida); // lo borramos de la lista global
                    }
                }
                try {
                    socketCliente.close(); // cerramos su conexion definitivamente
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}