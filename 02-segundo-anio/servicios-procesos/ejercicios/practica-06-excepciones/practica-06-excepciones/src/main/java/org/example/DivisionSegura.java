package org.example;

import org.example.LimiteCienException;
import org.example.NumeroInvalidoException;

import java.util.Scanner;

public class DivisionSegura {
    public static void main(String[] args) {
        // Inicializamos el Scanner para leer datos del usuario
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Introduce el primer número (dividendo): ");
            int numero1 = scanner.nextInt();

            // Si numero1 es menor o igual a 0, lanza NumeroInvalidoException
            if (numero1 <= 0) {
                throw new NumeroInvalidoException("el numero es menor o igual a 0");
            }

            // Si numero1 es menor o igual a 100, lanza LimiteCienException
            if ( numero1 <= 100 ) {
                throw new LimiteCienException("el numero es menor o igual a 100");
            }

            System.out.print("Introduce el segundo número (divisor): ");
            int numero2 = scanner.nextInt();

            // Realizar la división.
            // Nota: Si numero2 es 0, el motor de Java lanzará automáticamente un ArithmeticException.
            int resultado = numero1 / numero2;
            System.out.println("✅ El resultado de la división es: " + resultado);

        } catch (ArithmeticException e) {
            // Capturamos el error nativo de dividir por cero
            System.out.println("🚨 Error Crítico: No se puede dividir por cero.");

        } catch (NumeroInvalidoException | LimiteCienException e) {
            // Capturamos nuestras reglas de negocio usando e.getMessage() para imprimir el error exacto
            System.out.println("⚠️ Error de Negocio: " + e.getMessage());

        } catch (Exception e) {
            // Un "catch-all" por si ocurre algo muy raro (ej. el usuario introduce una letra en vez de número)
            System.out.println("❌ Error Inesperado: Ha ocurrido un fallo no contemplado.");

        } finally {
            // Este bloque SIEMPRE se ejecuta, haya error o no. Ideal para cerrar conexiones/archivos.
            scanner.close();
            System.out.println("🔒 Ejecución finalizada. Recursos liberados.");
        }
    }
}