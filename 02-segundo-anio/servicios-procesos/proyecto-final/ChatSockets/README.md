# 💬 Chat TCP Multi-Hilo | Arquitectura Cliente-Servidor

![Status](https://img.shields.io/badge/Estado-Completado_🚀-2ea44f?style=for-the-badge)
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![JavaFX](https://img.shields.io/badge/JavaFX-FF8000?style=for-the-badge&logo=java&logoColor=white)
![Linux](https://img.shields.io/badge/Linux-FCC624?style=for-the-badge&logo=linux&logoColor=black)

Sistema de mensajería bidireccional y concurrente construido en Java puro. Este proyecto implementa una arquitectura Cliente/Servidor robusta basada en conexiones por Sockets TCP y procesamiento Multi-hilo, permitiendo que múltiples usuarios interactúen simultáneamente en una sala de chat centralizada.

## 🧠 Diseño del Sistema y Concurrencia

Si el servidor procesara las peticiones de forma secuencial, la lectura de un mensaje bloquearía al resto de usuarios. Para solucionar esto, el sistema se diseñó bajo los siguientes pilares técnicos:

- **Delegación de Hilos (Multithreading):** El servidor escucha en un bucle infinito mediante `accept()`. Por cada conexión entrante, se instancia un objeto que implementa la interfaz `Runnable`, asignando un hilo independiente proporcionado por el sistema operativo para gestionar ese cliente.
- **Mecanismo de Broadcast (Sincronización):** Para replicar los mensajes a toda la sala, el servidor almacena los flujos de salida de cada cliente en una estructura global (`HashSet<PrintWriter>`). Se utiliza el bloque `synchronized` al añadir o iterar sobre esta lista para prevenir colisiones de memoria (`ConcurrentModificationException`) en un entorno concurrente.
- **Flujos de Datos Asíncronos (I/O Streams):** Implementación de `BufferedReader` y `PrintWriter` para separar y gestionar independientemente los canales de lectura y escritura en la red.

## 🖥️ Interfaz Gráfica y Despliegue

- **UI Reactiva con JavaFX:** La interfaz gráfica se diseñó separando el motor de red en un hilo secundario. Para evitar congelar la interfaz, las actualizaciones visuales tras la recepción de un mensaje por el Socket se orquestan de forma segura utilizando `Platform.runLater()` hacia el hilo principal de JavaFX.
- **Virtualización y Pruebas en Linux (WSL):** El servidor fue compilado (`javac`) y desplegado nativamente en un subsistema Linux (Ubuntu), logrando una comunicación cruzada exitosa con clientes visuales ejecutándose desde entornos Windows apuntando a la IP de la máquina virtual.

---

_⭐ Proyecto desarrollado por [David Valadés Navarro](https://github.com/davidValades) - Asignatura: Programación de Servicios y Procesos._
