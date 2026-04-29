content = """# 🎬 Tarea 6: Catálogo de Películas Dinámico (JSON + JS)

![Status](https://img.shields.io/badge/Estado-En_Desarrollo_🚀-2ea44f?style=for-the-badge)
![JavaScript](https://img.shields.io/badge/javascript-%23323330.svg?style=for-the-badge&logo=javascript&logoColor=%23F7DF1E)
![JSON](https://img.shields.io/badge/JSON-000000?style=for-the-badge&logo=json&logoColor=white)

## 📝 Descripción

Este proyecto consiste en la creación de un catálogo de películas interactivo donde la información no está "hardcodeada" en el HTML, sino que se consume desde un archivo externo en formato **JSON**. El objetivo principal es practicar la manipulación dinámica del DOM y el manejo de un flujo de datos asíncrono básico en el Frontend.

Este ejercicio forma parte del módulo de **Desarrollo de Interfaces** del segundo año de DAM DUAL.

## 🎯 Objetivos de la Actividad

Siguiendo los requerimientos técnicos de la **Práctica XML y JS** ([Actividad.pdf]), el proyecto cumple con:

- [x] **Gestión de Datos:** Creación de un origen de datos externo (JSON) con un mínimo de 8 películas.
- [x] **Esquema de Información:** Cada entrada incluye dirección, duración, nacionalidad, actores, género y sinopsis.
- [x] **Interfaz Dinámica:** Disposición básica en HTML preparada para recibir nodos generados por JS.
- [x] **Lógica de Programación:** - Conexión con el archivo JSON mediante la Fetch API.
  - Creación de un array de objetos para el manejo de la información.
  - Pintado automático de nodos (cards) en el DOM.
  - Evento de clic en la imagen para revelar la ficha técnica de la película.

## 🛠️ Stack Tecnológico

- **HTML5:** Estructura para el catálogo.
- **CSS3:** Maquetación de la rejilla de películas y efectos visuales.
- **JavaScript (ES6+):** Manipulación del DOM y Fetch API.
- **JSON:** Estructura de datos ligera.

## 📁 Estructura del Proyecto

```text
tarea6/
├── index.html          # Punto de entrada y contenedor del catálogo
├── style.css           # Estilos para la disposición y diseño visual
├── main.js             # Lógica: Fetch de datos, creación de nodos y eventos
├── movies.json         # Fuente de datos con las 8 películas requeridas
└── README.md           # Documentación de la tarea (este archivo)
```

⭐ Ejercicio realizado para el módulo de Desarrollo de Interfaces - [David Valadés Navarro](https://github.com/davidValades)
