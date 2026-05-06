# 🛡️ Práctica 06: Manejo de Excepciones y Reglas de Negocio

![Status](https://img.shields.io/badge/Estado-Completado_✅-2ea44f?style=for-the-badge)
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)

Este proyecto aborda uno de los pilares de la programación Backend y la robustez del software: el **control de excepciones**. El objetivo no es solo evitar que el programa colapse, sino aprender a capturar errores específicos y devolver mensajes claros, simulando el comportamiento de una API profesional.

## 🎯 Objetivo de la Práctica

Diseñar un programa interactivo que realice la división matemática de dos números, controlando el flujo de ejecución mediante excepciones para cumplir con las siguientes reglas de negocio obligatorias:

1. **Protección Aritmética:** El segundo número (divisor) no puede ser cero
2. **Validación Positiva:** El primer número (dividendo) no puede ser cero ni menor que cero.
3. **Límite de Operación:** El primer valor introducido debe ser estrictamente mayor que 100.

## 🧠 Conceptos Técnicos Aplicados

* **Excepciones Nativas (`ArithmeticException`):** Captura de errores generados directamente por el motor del lenguaje (como la división por cero).
* **Excepciones Personalizadas (Custom Exceptions):** Creación de clases propias que heredan de `Exception` para manejar reglas de negocio específicas (ej. `ValorMenorCienException`).
* **Bloques `try-catch-finally`:** Aislamiento del código propenso a fallos para garantizar que la aplicación se recupere con gracia ante una entrada de datos inválida del usuario.

## 💻 Esqueleto del Código (Lógica a Implementar)
```java
// ==========================================
// 1. Definición de Excepciones Personalizadas
// ==========================================
// TODO: Crear clase 'NumeroInvalidoException' para manejar cuando el número es <= 0.
// TODO: Crear clase 'LimiteCienException' para manejar cuando el número <= 100.

// ==========================================
// 2. Método Principal (Main)
// ==========================================
public class DivisionSegura {
    public static void main(String[] args) {
        // Inicializar Scanner para leer datos del usuario
        
        try {
            // 1. Pedir el primer número
            // 2. Validar: ¿Es <= 0? -> Lanzar excepción[cite: 13]
            // 3. Validar: ¿Es <= 100? -> Lanzar excepción[cite: 13]
            
            // 4. Pedir el segundo número
            
            // 5. Realizar la división
            // (El propio lenguaje lanzará ArithmeticException si el segundo es 0)[cite: 13]
            
            // 6. Imprimir resultado
            
        } catch (ArithmeticException e) {
            // Capturar intento de dividir por cero[cite: 13]
            System.out.println("🚨 Error Crítico: No se puede dividir por cero.");
        } catch (Exception e) { // Cambiar por tus excepciones personalizadas
            // Capturar el resto de reglas de negocio[cite: 13]
            System.out.println("⚠️ Error de Negocio: " + e.getMessage());
        } finally {
            // Cerrar el Scanner para liberar recursos en memoria
            System.out.println("🔒 Ejecución finalizada. Recursos liberados.");
        }
    }
}
```

---

## 🛠️ Instrucciones de Uso

1. Clonar el repositorio y navegar a la carpeta del proyecto.
2. Compilar el archivo fuente:
```bash
javac DivisionSegura.java
```
3. Ejecutar el programa interactivo:
```bash
java DivisionSegura
```

---

⭐ **Práctica documentada por David Valadés Navarro**
