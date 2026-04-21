# ⚡ Digitech Core Exercises | Logic & DOM Manipulation

![Status](https://img.shields.io/badge/Estado-Completado_✅-2ea44f?style=for-the-badge)
![JavaScript](https://img.shields.io/badge/javascript-%23323330.svg?style=for-the-badge&logo=javascript&logoColor=%23F7DF1E)
![HTML5](https://img.shields.io/badge/html5-%23E34F26.svg?style=for-the-badge&logo=html5&logoColor=white)

Este repositorio contiene una serie de retos prácticos enfocados en la modificación de algoritmos lógicos, manipulación del DOM y gestión de recursos del sistema. El objetivo es profundizar en el control de flujos y la adaptabilidad del software según el entorno del usuario.

---

## 🧠 Retos y Soluciones Aplicadas

El proyecto se divide en 5 bloques lógicos donde se han transformado programas base para cumplir con requisitos específicos de negocio y técnicos:

### 1. Modificación Algorítmica (Múltiplos)
* **Múltiplos de 3:** Reestructuración del bucle de control para identificar y filtrar secuencias basadas en la divisibilidad por 3.
* **Múltiplos de 11:** Implementación de lógica de salto para la generación eficiente de múltiplos de 11.

### 2. Gestión de Tiempo y UI Dinámica
* **Huso Horario Canarias:** Ajuste de la clase `Date` para reflejar el desfase horario (UTC+0/UTC+1).
* **Formato 12h/24h:** Implementación de condicionales para la conversión de horas y sufijos AM/PM según la elección del usuario.
* **Dark Mode Automático:** Inyección dinámica de estilos CSS para cambiar el fondo a *Azul Noche* basándose en el rango horario detectado.

### 3. Gestión de Datos (Día del Idioma 📚)
* **Extensión de Tareas:** Evolución de un gestor de tareas simple a una biblioteca de libros y autores.
* **Inmortalización Cultural:** Integración de recursos visuales y metadatos en honor al 23 de abril (Día del Idioma).

### 4. 🚀 Optimización y Control de Hardware (Punto Bonus)
* **Gestión de Potencia:** Se ha implementado un "Safe Guard" que limita el procesamiento de números primos basándose en el desbordamiento de memoria/tiempo de ejecución observado.
* **UX Preventiva:** Uso de `alert()` informativos para educar al usuario sobre las limitaciones de hardware antes de que el navegador se bloquee.
* **Criba de Eratóstenes:** Inclusión de una sección informativa sobre la importancia de este algoritmo en la **Criptografía Moderna** y la seguridad informática.

---

## 🛠️ Tecnologías Utilizadas

- **Lógica de Programación:** JavaScript (ES6+)
- **Estructura:** HTML5 Semántico
- **Estilos:** CSS3 Dinámico
- **Conceptos de Computación:** Algoritmos de Cribado y Eficiencia de Ciclos.

---

## 📂 Estructura del Ejercicio

```text
digitech-ejercicios/
├── index.html              # Dashboard principal con acceso a los retos
├── js/
│   ├── multiplos.js        # Lógica de múltiplos de 3 y 11
│   ├── reloj.js            # Gestión de hora y estilos de noche
│   ├── biblioteca.js       # Lista de libros y autores
│   └── primos_expert.js    # Criba de Eratóstenes y control de potencia
└── assets/                 # Imágenes conmemorativas y recursos visuales