# ⚡ Digitech Core Exercises | Logic, Performance & DOM Manipulation

![Status](https://img.shields.io/badge/Estado-Completado_✅-2ea44f?style=for-the-badge)
![JavaScript](https://img.shields.io/badge/javascript-%23323330.svg?style=for-the-badge&logo=javascript&logoColor=%23F7DF1E)
![HTML5](https://img.shields.io/badge/html5-%23E34F26.svg?style=for-the-badge&logo=html5&logoColor=white)
![CSS3](https://img.shields.io/badge/css3-%231572B6.svg?style=for-the-badge&logo=css3&logoColor=white)

Este repositorio contiene una serie de retos prácticos avanzados enfocados en la eficiencia algorítmica, la manipulación del DOM en tiempo real y la gestión de la experiencia de usuario (UX). Se ha evolucionado desde scripts básicos de consola hasta un Dashboard interactivo y modular.

---

## 🧠 Retos y Soluciones Aplicadas

El proyecto se divide en 4 bloques funcionales donde se ha priorizado el rendimiento, la escalabilidad y las buenas prácticas:

### 1. ⏱️ Reloj, Gestión de Estados y UI Dinámica
* **Motor en Tiempo Real:** Uso de `setInterval()` para la actualización continua del DOM sin recargar la página.
* **Gestión de Zonas Horarias:** Implementación de variables de estado (`esCanarias`) para alternar dinámicamente entre la hora peninsular y UTC+0.
* **Modals Customizados:** Sustitución de los `alert()` y `confirm()` nativos del navegador por ventanas modales en HTML/CSS, mejorando drásticamente la UX al alternar entre formatos de 12h y 24h.
* **Dark Mode Automático:** Inyección y manipulación de variables nativas de CSS (`:root`) mediante JavaScript (`setProperty`) para transicionar a un tema *Azul Noche* basándose en la hora detectada.

### 2. 🚀 Criptografía y Hardware Safeguard (Punto Bonus)
* **Criba de Eratóstenes vs Fuerza Bruta:** Laboratorio de pruebas para comparar el cálculo tradicional de números primos frente a algoritmos optimizados.
* **Benchmarking:** Uso de `performance.now()` para medir el tiempo de ejecución en milisegundos con alta precisión.
* **Protección del Navegador (Safe Guards):** * Bloqueo de peticiones superiores a 5.000.000 para evitar el desbordamiento de memoria.
  * Límite de renderizado en el DOM (máximo 1.000 nodos inyectados visualmente) previniendo el colapso del "Main Thread" del navegador, ocultando el resto de forma segura.

### 3. 🔄 Eficiencia en Bucles (Múltiplos de 3 y 11)
* **Algoritmia de Saltos:** Demostración empírica de cómo un bucle condicional (`i % 3 === 0`) es infinitamente menos eficiente que una reestructuración de la lógica de iteración (`i += 3` y `i += 11`). Reducción drástica del coste computacional.

### 4. 📚 DOM CRUD (Books To-Do List)
* **Manipulación Directa del Árbol DOM:** Aplicación de gestión de libros implementando `document.createElement()` y `appendChild()`.
* **Delegación y Destrucción:** Asignación dinámica de `EventListeners` a nodos recién creados para permitir el borrado individualizado de registros (`element.remove()`).

---

## 🛠️ Tecnologías y Conceptos Clave

- **Lógica de Programación:** JavaScript (ES6+), State Management, Time/Date manipulation.
- **Rendimiento:** Web Performance APIs (`performance.now()`), Prevención de cuellos de botella en el DOM.
- **Estructura:** HTML5 Semántico, Modularity.
- **Estilos:** CSS3, Flexbox, CSS Grid, CSS Custom Properties (Variables), Animaciones y Transiciones.

---

## 📂 Arquitectura del Proyecto

```text
digitech-ejercicios/
├── index.html                  # Dashboard principal
├── pages/                      # Vistas funcionales
│   ├── reloj.html
│   ├── primos.html
│   ├── multiplos.html
│   ├── multiplos11.html
│   └── books.html
├── css/                        # Hojas de estilo modulares
│   ├── styles.css              # Globales, Nav y Dashboard
│   ├── reloj/style_1.css       # Layout del reloj y Modals
│   ├── primos/style.css        # Layout de métricas y scroll-boxes
│   └── books/style.css         # Interfaz de la lista de tareas
└── js/                         # Lógica separada por dominio
    ├── main.js                 # Comportamiento global (Menú Hamburguesa)
    ├── reloj.js                # Lógica de tiempo real y estados
    ├── primos.js               # Algoritmo de Eratóstenes y Benchmarking
    ├── multiplos.js            # Algoritmos iterativos base 3
    ├── multiplos11.js          # Algoritmos iterativos base 11
    └── books.js                # Lógica CRUD de nodos HTML
```