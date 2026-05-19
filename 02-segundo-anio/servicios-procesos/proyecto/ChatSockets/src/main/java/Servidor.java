import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Servidor {
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
        }catch (IOException e) {
            System.out.println("Error al inicializar servidor "+ e.getMessage()); //cubrimos el error por si falla.
        }
    }
}

//Clases para manejar los clientes
class ManejadorCliente implements Runnable {
    private Socket socketCliente;

    public ManejadorCliente(Socket socket) {
        this.socketCliente = socket;
    }

    @Override
    public void run() {
        System.out.println("Hilo iniciado para atender al cliente");
        // aqui agregamos posteriormente la logica para leer y enviar mensajes
    }
}
