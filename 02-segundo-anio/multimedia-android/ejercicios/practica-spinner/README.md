# 🧮 Calculadora con Spinner | Android Studio

![Status](https://img.shields.io/badge/Estado-Completado-2ea44f?style=for-the-badge)
![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Java](https://img.shields.io/badge/Java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)

Segunda práctica del módulo de **Programación Multimedia y Dispositivos Móviles** del ciclo DAM. Consiste en una calculadora básica que utiliza un menú desplegable (`Spinner`) para seleccionar el tipo de operación matemática a realizar.

## 🎯 Objetivo y Funcionalidad

El objetivo de la aplicación es recoger dos valores numéricos introducidos por el usuario, leer la operación matemática seleccionada en un menú desplegable y mostrar el resultado exacto al pulsar un botón.

**Flujo de la Aplicación:**

1. **Entrada de Datos:** El usuario introduce dos números en dos cajas de texto (`EditText`).
2. **Selección de Operación:** Mediante un `Spinner`, el usuario elige entre:
   - Sumar
   - Restar
   - Multiplicar
   - Dividir
3. **Procesamiento:** Al pulsar el botón de calcular, el sistema lee las entradas, realiza el cálculo y formatea el resultado.
4. **Salida:** El resultado se muestra en una etiqueta de texto (`TextView`) en la parte inferior de la pantalla.

## 🛠️ Conceptos Técnicos Aplicados

- Uso de `EditText` con restricción de entrada numérica (`inputType="numberDecimal"`).
- Implementación y población de un `Spinner` mediante un `ArrayAdapter` y recursos de strings (`strings.xml`).
- Captura y parseo de datos de tipo `String` a `Double`.
- Manejo de excepciones básicas (ej. control de división por cero o campos vacíos).
