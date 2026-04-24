# 🧠 Memory Game | Android Studio

![Status](https://img.shields.io/badge/Estado-Completado-2ea44f?style=for-the-badge)
![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Java](https://img.shields.io/badge/Java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)

Primera práctica del módulo de **Programación Multimedia y Dispositivos Móviles** del ciclo DAM. Consiste en el desarrollo clásico del juego de memoria emparejando cartas, centrado en el manejo del estado de la UI y eventos de botones en Android.

## 🎯 Objetivo y Mecánicas

El objetivo del juego es encontrar todas las parejas de figuras iguales (círculo, cuadrado, estrella, triángulo, corazón, luna).

**Reglas de negocio implementadas:**

1. **Flujo de turno:** El usuario selecciona dos cartas (que se voltean) y pulsa un botón de "Comprobar".
2. **Validación:** - Si son iguales: Las cartas quedan descubiertas.
   - Si son distintas: Las cartas se vuelven a ocultar.
3. **Sistema de Puntuación:**
   - Acierto: `+20 puntos`.
   - Penalización: `-5 puntos` cada 3 fallos consecutivos.
   - Restricción: La puntuación mínima nunca baja de `0`.
4. **Reinicio:** Un botón dedicado permite resetear el tablero y los contadores a su estado inicial.

## 🛠️ Conceptos Técnicos Aplicados

- Uso de `ImageButton` para la representación visual de las cartas.
- Gestión de recursos gráficos (Drawable).
- Manejo de Eventos (`setOnClickListener`).
- Lógica condicional para el seguimiento de turnos y puntuación.
