# 🎬 Tarea 6: Catálogo de Películas Dinámico (JSON + JS)

![Status](https://img.shields.io/badge/Estado-Completado_✅-2ea44f?style=for-the-badge)
![JavaScript](https://img.shields.io/badge/javascript-%23323330.svg?style=for-the-badge&logo=javascript&logoColor=%23F7DF1E)
![JSON](https://img.shields.io/badge/JSON-000000?style=for-the-badge&logo=json&logoColor=white)

## 📝 Descripción

Este proyecto consiste en la creación de un catálogo de películas interactivo donde la información no está "hardcodeada" en el HTML, sino que se consume desde un archivo externo en formato **JSON**. El objetivo principal es practicar la manipulación dinámica del DOM y el manejo de un flujo de datos asíncrono básico en el Frontend.

Este ejercicio forma parte del módulo de **Desarrollo de Interfaces** del segundo año de DAM DUAL.

## 🎯 Objetivos de la Actividad

Siguiendo los requerimientos técnicos de la **Práctica XML y JS**, el proyecto cumple con:

- [x] **Gestión de Datos:** Creación de un origen de datos externo (JSON) con 8 películas y consumo de imágenes externas mediante CDN (TMDB).
- [x] **Esquema de Información:** Cada entrada incluye dirección, duración, nacionalidad, actores, género y sinopsis.
- [x] **Interfaz Dinámica:** Disposición básica en HTML utilizando CSS Grid para organizar las tarjetas de las películas de forma responsiva.
- [x] **Lógica de Programación:** - Conexión asíncrona (`async/await`) con el archivo JSON mediante la Fetch API.
  - Destructuración de objetos y creación dinámica de nodos en el DOM (`createElement`, `appendChild`).
  - **Mejora UI/UX:** Evento de clic en la imagen para revelar la ficha técnica de la película inyectando los datos en un **Modal dinámico y reutilizable**, manteniendo el DOM ligero.

## 🛠️ Stack Tecnológico

- **HTML5:** Estructura semántica.
- **CSS3:** Maquetación de la rejilla (Grid), Flexbox para el modal, variables nativas y animaciones de transición.
- **JavaScript (ES6+):** Manipulación del DOM, Fetch API, Template Literals y gestión del alcance de variables (Scope).
- **JSON:** Estructura de datos ligera.

## 📁 Estructura del Proyecto

```text
Tarea6/
├── index.html
├── README.md
└── assets/
    ├── css/
    │   └── styles.css
    ├── js/
    │   └── main.js
    └── json/
        └── movies.json
```

---

_⭐ Ejercicio documentado por [David Valadés Navarro](https://github.com/davidValades) - Digitech FP._
