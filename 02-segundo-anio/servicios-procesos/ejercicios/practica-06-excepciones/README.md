# 🛡️ Práctica 06: Manejo de Excepciones y Reglas de Negocio

![Status](https://img.shields.io/badge/Estado-Completado_✅-2ea44f?style=for-the-badge)
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)

Este proyecto aborda uno de los pilares de la programación Backend y la robustez del software: el **control de excepciones**. El objetivo no es solo evitar que el programa colapse, sino aprender a aislar errores específicos y devolver mensajes claros, simulando el comportamiento de una API profesional.

## 🎯 Objetivo de la Práctica

Diseñar un programa interactivo que realice la división matemática de dos números, controlando el flujo de ejecución mediante excepciones para cumplir con las siguientes reglas de negocio obligatorias:

1. **Protección Aritmética:** El segundo número (divisor) no puede ser cero.
2. **Validación Positiva:** El primer número (dividendo) no puede ser cero ni menor que cero.
3. **Límite de Operación:** El primer valor introducido debe ser estrictamente mayor que 100.

## 🧠 Conceptos Técnicos Aplicados

* **Excepciones Nativas (`ArithmeticException`):** Captura de errores generados directamente por el motor de Java (como la división por cero).
* **Excepciones Personalizadas (Custom Exceptions):** Creación de clases propias que heredan de la clase `Exception` para manejar reglas de negocio específicas.
* **Bloques `try-catch-finally`:** Aislamiento del código propenso a fallos para garantizar que la aplicación se recupere con gracia ante una entrada de datos inválida, liberando siempre los recursos en memoria (ej. cierre del `Scanner`).

## 📁 Estructura del Proyecto
```text
practica-06-excepciones/
├── src/
│   ├── DivisionSegura.java           # Clase principal y lógica del programa
│   ├── NumeroInvalidoException.java  # Excepción custom: números <= 0
│   └── LimiteCienException.java      # Excepción custom: números <= 100
└── README.md
```

## 💻 Esqueleto del Código (Lógica Implementada)

```Java
public class DivisionSegura {
    public static void main(String[] args) {
        // Inicialización de recursos (Scanner)
        
        try {
            // 1. Pedir validaciones de negocio (Lanza Custom Exceptions)
            // 2. Realizar la división matemática (Puede lanzar ArithmeticException)
            // 3. Imprimir resultado exitoso
            
        } catch (ArithmeticException e) {
            System.out.println("🚨 Error Crítico: No se puede dividir por cero.");
        } catch (NumeroInvalidoException | LimiteCienException e) { 
            System.out.println("⚠️ Error de Negocio: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Error Inesperado: Ha ocurrido un fallo no contemplado.");
        } finally {
            System.out.println("🔒 Ejecución finalizada. Recursos liberados.");
        }
    }
}
```

## 🚀 Ejemplos de Ejecución (Outputs)

**✅ Caso de Éxito:**

```Plaintext
> Introduce el primer número: 150
> Introduce el segundo número: 2
Resultado: 75
🔒 Ejecución finalizada. Recursos liberados.
```


**⚠️ Violación de Regla de Negocio (Límite 100):**
```Plaintext
> Introduce el primer número: 50
⚠️ Error de Negocio: El número debe ser estrictamente mayor a 100.
🔒 Ejecución finalizada. Recursos liberados.
```

**🚨 Fallo del Lenguaje (División por cero):**
```Plaintext
> Introduce el primer número: 200
> Introduce el segundo número: 0
🚨 Error Crítico: No se puede dividir por cero.
🔒 Ejecución finalizada. Recursos liberados.
```

_⭐ Práctica documentada por David Valadés Navarro
