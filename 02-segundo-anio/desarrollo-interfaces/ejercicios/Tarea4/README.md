# 📝 Tarea 4: Gestor de Calificaciones y Promoción

![Status](https://img.shields.io/badge/Estado-Completado_✅-2ea44f?style=for-the-badge)
![HTML5](https://img.shields.io/badge/html5-%23E34F26.svg?style=for-the-badge&logo=html5&logoColor=white)
![JavaScript](https://img.shields.io/badge/javascript-%23323330.svg?style=for-the-badge&logo=javascript&logoColor=%23F7DF1E)

## 🎯 Objetivo del Ejercicio

Desarrollar una interfaz funcional para la inserción y procesamiento de notas de alumnos. El sistema debe validar el rendimiento académico, calcular promedios y aplicar la lógica de negocio para determinar si un estudiante es apto para el periodo de prácticas o debe repetir el curso.

---

## 🧠 Lógica de Negocio Implementada

### 1. Calificaciones (Criterio de Evaluación)

El sistema promedia las tres asignaturas y asigna una mención según el resultado:

- **0 - 4.9**: SUSPENSO
- **5 - 6.9**: APROBADO
- **7 - 8.9**: NOTABLE
- **9 - 9.9**: SOBRESALIENTE
- **10**: MATRÍCULA DE HONOR

### 2. Algoritmo de Promoción

Para avanzar al siguiente nivel, se aplican las siguientes reglas:

- **✅ Apto**: Las tres notas deben ser iguales o mayores a 5.
  - _Mensaje_: "Puede iniciar su período de prácticas."
- **⚠️ Pendiente**: Una nota es inferior a 5.
  - _Mensaje_: Estudiante con asignatura pendiente (bloquea prácticas).
- **❌ Repetición**: Dos o más notas inferiores a 5.
  - _Mensaje_: El alumno debe repetir el curso.

---

## 🛠️ Tecnologías Utilizadas

- **HTML5**: Estructura del formulario semántico e intuitivo.
- **JavaScript (Vanilla)**: Lógica de cálculo, validaciones de tipos de datos y gestión de alertas interactivas.
- **CSS3**: Diseño orientado a la temática educativa (imágenes y UX).

---

## 📂 Estructura de la Tarea

```text
tarea-04/
├── index.html       # Interfaz de usuario
├── script.js        # Motor de lógica y cálculos
├── style.css        # Estilos y diseño visual
└── assets/          # Imágenes relacionadas
```
