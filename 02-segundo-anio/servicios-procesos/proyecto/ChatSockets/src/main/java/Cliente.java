import java.io.IOException;
import java.net.Socket;

public class Cliente {
    public static void main(String[] args) {
        //variables para la ip (la ip de la maquina virtual que creemos.
        String ipServidor = "127.0.0.1";
        int puerto = 5000; //puerto que tenemos en la clase servidor

        try {
            System.out.println("Iniciando conexion al servidor en " +ipServidor+ ":" +puerto+ "...");

            Socket socket = new Socket(ipServidor, puerto); //instanciamos el socket con ip y servidor
            System.out.println("¡Conectado al servidor!");

            socket.close(); //cerramos conexion de socket
        }
        catch (IOException e) {
            System.out.println("Error al conectar con el servidor: "+e.getMessage()); //manejamos error de conexion
        }
    }
}
