package org.example;

public class NumeroInvalidoException extends Exception {

    // Constructor que recibe el mensaje de error
    public NumeroInvalidoException(String mensaje) {
        // La palabra 'super' llama al constructor de la clase padre (Exception)
        // Esto permite que Java guarde el mensaje y luego podamos usar e.getMessage()
        super(mensaje);
    }
}
