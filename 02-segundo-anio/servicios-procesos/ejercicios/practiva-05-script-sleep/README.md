# 🐧 Orquestación de Procesos en Bash (Script Sleep)

![Status](https://img.shields.io/badge/Estado-En_Desarrollo_🚀-2ea44f?style=for-the-badge)
![Linux](https://img.shields.io/badge/Linux-FCC624?style=for-the-badge&logo=linux&logoColor=black)
![Bash](https://img.shields.io/badge/Bash-4EAA25?style=for-the-badge&logo=gnu-bash&logoColor=white)

Este proyecto es un cuaderno de laboratorio enfocado en la automatización y el control de procesos mediante *Shell Scripting*. El script interactivo demuestra la capacidad de gestionar tareas en primer y segundo plano, enviar señales al sistema operativo y sincronizar procesos asíncronos.

## 🎯 Objetivo del Script

Desarrollar un script ejecutable en Bash que cumpla con 15 directivas específicas de control de flujo, incluyendo:
* Creación de procesos asíncronos (Background).
* Suspensión temporal de la ejecución (`sleep`).
* Envío de señales de control (`SIGSTOP`, `SIGCONT`, `SIGTERM`).
* Sincronización de hilos de ejecución (`wait`).

## 🧠 Conceptos Técnicos Aplicados

* **Gestión de PIDs Automática:** Uso de la variable especial `$!` para capturar el identificador del último proceso enviado a segundo plano.
* **Control de Señales (Signals):** Interacción directa con el Kernel de Linux para pausar y reanudar procesos sin destruirlos.
* **Sincronización:** Uso de la directiva `wait` para evitar condiciones de carrera (Race Conditions) asegurando que un proceso dependiente no finalice antes de tiempo.

## 💻 Ejecución del Script

Para ejecutar esta automatización en cualquier entorno Linux:

1. Clonar el repositorio y navegar a la carpeta del script.
2. Otorgar permisos de ejecución:
   ```bash
   chmod +x orquestador_procesos.sh
   ```
3. Lanza el script:
    ```bash
   ./orquestador_procesos.sh
   ```

---

⭐ **Práctica documentada por David Valadés Navarro**