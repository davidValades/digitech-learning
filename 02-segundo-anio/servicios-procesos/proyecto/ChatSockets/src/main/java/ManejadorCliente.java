import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

//Clases para manejar los clientes
class ManejadorCliente implements Runnable {
    private Socket socketCliente;

    public ManejadorCliente(Socket socket) {
        this.socketCliente = socket;
    }

    @Override
    public void run() {
        try {
            System.out.println("Hilo iniciado para atender al cliente");

            // Preparamos el canal para leer (entrada)
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));

            // Canal para enviar la respuesta
            PrintWriter salida = new PrintWriter(socketCliente.getOutputStream(), true);

            String mensajeCliente;

            while ((mensajeCliente = entrada.readLine()) != null) {
                System.out.println(" Cleinte: " + mensajeCliente);

                salida.println("Servidor recibió: "+ mensajeCliente);
            }
        }
        catch (IOException e) {
            System.out.println("Error en la comunicaion con el cliente: "+e.getMessage());
        } finally {
            try {
                socketCliente.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}