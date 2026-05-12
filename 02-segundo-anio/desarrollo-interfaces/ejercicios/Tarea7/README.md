# Tarea de Interfaz: Cuestionario Interactivo de JavaScript

Este repositorio contiene la resolución de la Tarea 7 de la asignatura de Desarrollo de Interfaces. El objetivo es demostrar el conocimiento de los fundamentos de JavaScript y su integración con HTML y CSS.

## 📁 Estructura del Proyecto

El proyecto está compuesto por tres archivos fundamentales:

1.  **index.html**: Contiene la estructura de la página, el cuestionario y la tabla comparativa.
2.  **style.css**: Define el diseño y la presentación visual de la tabla y los elementos de la página.
3.  **script.js**: Contiene la lógica interactiva que responde a las preguntas mediante el uso de `alert()`.

---

## ❓ Cuestionario Técnico (Respuestas incluidas en el JS)

En el archivo `script.js` se da respuesta a las siguientes preguntas mediante ventanas emergentes (`alert`):

1.  **¿Qué es JavaScript y su función principal?**
    Es un lenguaje de programación interpretado, orientado a objetos y basado en prototipos. Su función principal es añadir interactividad, dinamismo y lógica a las páginas web.

2.  **Diferencia entre `let` y `var`:**
    - `var`: Tiene un ámbito de función y permite el _hoisting_. Es la forma antigua de declarar variables.
    - `let`: Tiene un ámbito de bloque (limitado a las llaves `{}` donde se declara) y es la forma moderna y segura de manejar variables.

3.  **¿Qué es el DOM?**
    El _Document Object Model_ es una interfaz de programación que representa el documento HTML como una estructura de árbol. JavaScript interactúa con él para modificar contenido, estilos y estructura en tiempo real.

4.  **Tipos de datos primitivos en JS:**
    - `String` (Cadenas de texto)
    - `Number` (Números)
    - `Boolean` (Verdadero/Falso)
    - `Undefined` (Variable declarada pero no asignada)
    - `Null` (Valor nulo intencionado)

5.  **¿Qué es una función?**
    Es un bloque de código diseñado para realizar una tarea específica, que se ejecuta cuando es "invocado" o llamado. Se utiliza para reutilizar código y organizar la lógica.

6.  **Importación de librerías:**
    Se importan mediante la etiqueta `<script src="url_o_ruta_de_la_libreria"></script>` en el HTML o mediante `import` en módulos modernos de JS.

7.  **Ubicación del script (Body vs Head):**
    Se enlaza antes de cerrar el `</body>` para asegurar que el HTML se haya cargado completamente antes de ejecutar el código JS. Si se inserta en el `<head>`, el script puede intentar acceder a elementos del DOM que aún no existen, provocando errores (a menos que se use `defer` o `async`).

8.  **Atributos `async` y `defer`:**
    - `async`: El script se descarga de forma asíncrona y se ejecuta inmediatamente al terminar la descarga, interrumpiendo el renderizado del HTML.
    - `defer`: El script se descarga de forma asíncrona pero solo se ejecuta cuando el navegador ha terminado de analizar (parsear) todo el documento HTML.

---

## 📊 Diferencias entre Java y JavaScript

| Característica       | Java                                      | JavaScript                              |
| :------------------- | :---------------------------------------- | :-------------------------------------- |
| **Tipo de Lenguaje** | Compilado (Bytecode)                      | Interpretado (por el navegador)         |
| **Tipado**           | Fuertemente tipado y estático             | Débilmente tipado y dinámico            |
| **Plataforma**       | JVM (Java Virtual Machine)                | Navegador o Node.js                     |
| **Orientación**      | Orientado a objetos (Clases)              | Basado en prototipos                    |
| **Uso Principal**    | Aplicaciones backend, Android, Escritorio | Interactividad Frontend, Desarrollo Web |

---

**Nota Académica:** Este contenido ha sido preparado como material de estudio para el examen de la asignatura.
