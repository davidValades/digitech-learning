# 🐧 Práctica: Gestión de Procesos y Comandos en Linux

![Status](https://img.shields.io/badge/Estado-En_Progreso_🚀-2ea44f?style=for-the-badge)
![Linux](https://img.shields.io/badge/Linux-FCC624?style=for-the-badge&logo=linux&logoColor=black)
![Bash](https://img.shields.io/badge/Bash-4EAA25?style=for-the-badge&logo=gnu-bash&logoColor=white)

Este documento sirve como cuaderno de laboratorio para la práctica de **"Programación Multiprocesos y Multihilos"**, correspondiente a la asignatura de **Programación de Servicios y Procesos (DAM DUAL)**.

---

## 🎯 Objetivo de la Práctica

El objetivo principal es adquirir soltura en la administración de sistemas Linux, específicamente en:

- La monitorización de recursos del sistema (`top`, `htop`).
- La gestión del ciclo de vida de los procesos (creación, interrupción y terminación).
- El control de trabajos en primer plano (foreground) y segundo plano (background).
- La gestión de permisos y ejecución de tareas entre usuarios estándar y el superusuario (`root`).

---

## 🛠️ Entorno de Trabajo

- **Sistema Operativo:** Entorno Linux (Máquina Virtual / WSL / Nativo).
- **Herramientas CLI:** `top`, `htop`, `jobs`, `ps`, `kill`.
- **Aplicaciones GUI utilizadas:** LibreOffice, Mozilla Firefox, Gedit.

---

## 📝 Desarrollo de la Práctica (Paso a Paso)

A continuación, se documentan las acciones solicitadas en la práctica junto con los comandos utilizados y su justificación técnica.

---

### 🔹 Paso 1: Conexión inicial

**Requerimiento:** Conecta como root para realizar las tareas.

    ```bash
    sudo su
    ```

**Explicación:**  
Se utiliza sudo su para elevar los privilegios del usuario estándar al entorno del superusuario (root). Esto es necesario para tener permisos globales sobre la gestión de procesos del sistema.

---

### 🔹 Paso 2: Lanzar proceso en primer plano

**Requerimiento:** Lanza el programa de LibreOffice.

    ```bash
    libreoffice
    ```

**Observación:**  
El comando se ejecuta en primer plano (foreground). Muestra una advertencia por la falta de un entorno Java (JVM), pero el proceso intenta iniciarse. Al estar en primer plano, la terminal queda bloqueada y no acepta nuevos comandos hasta que el proceso termine o sea interrumpido.

---

### 🔹 Paso 3: Lanzar proceso en segundo plano

**Requerimiento:** Lanza en segundo plano Firefox.

        ```bash
    firefox &
    ```

**Explicación:**  
El uso del operador & envía exitosamente el proceso a segundo plano, devolviendo un número de trabajo [1] y su PID (ej. 3077). Sin embargo, el proceso finaliza inmediatamente con un error de $XAUTHORITY. Esto ocurre como medida de seguridad de Linux: Firefox (y otros paquetes Snap) bloquea su ejecución con privilegios de root dentro de la sesión gráfica de un usuario estándar para prevenir vulnerabilidades de secuestro de sesión.

---

### 🔹 Paso 4: Monitorización de procesos

**Requerimiento:** Lista todos los procesos con la herramienta `top`.

    ```bash
    top
    ```

**Salida / Observación:**  
La herramienta `top` proporciona una vista dinámica y en tiempo real de los procesos del sistema. Columnas críticas como `%CPU` y `%MEM` permiten identificar cuellos de botella en el rendimiento, mientras que el `PID` es fundamental para gestionar el ciclo de vida del proceso (ej. enviar señales SIGKILL). Se pulsa la tecla `q` para salir.

---

### 🔹 Paso 5: Repetión de ejecución en background

**Requerimiento:** Lanza en segundo plano otra vez Firefox.

    ```bash
    firefox &
    ```

**Explicacion:** 
Se repite la acción del Paso 3 para consolidar el uso del operador `&`, el cual desacopla el proceso de la terminal actual, permitiendo que el usuario `root` continúe introduciendo comandos sin interrupción.

---

### 🔹 Paso 6: Encadenamiento y teporizadores

**Requerimiento:** Lanza el proceso de gedit, que abre el archivo de usuarios del sistema con un retardo de 20seg.

    ```bash
    (sleep 20 && gedit /etc/passwd) &
    ```

**Explicación:**
Este comando demuestra la orquestación básica de tareas en Bash.

    1. `sleep 20` introduce la pausa requerida.

    2. El operador `&&` asegura que `gedit` solo se ejecute tras finalizar la espera.

    3. El archivo objetivo es `/etc/passwd`, la base de datos local de usuarios del sistema.

    4. Se envuelve la instrucción en `()` y se añade `&` para enviar toda la secuencia al segundo plano (background), evitando que la terminal quede bloqueada durante los 20 segundos de espera.

## Paso 7: Ejecución con usuario estándar

```bash
firefox
```

**Explicación:**
Cuando ejecutas Firefox como usuario normal, el sistema usa correctamente la variable de entorno `$XAUTHORITY`, que contiene las credenciales necesarias para acceder al servidor gráfico X.

En cambio, al ejecutar como root, este usuario no tiene acceso por defecto al display del usuario original (por seguridad), lo que provoca el bloqueo. Esto evita que root interfiera con sesiones gráficas activas sin autorización.

---

## Paso 8: Monitorización desde el superusuario

```bash
top
```

**Observación:**
En `top`, el proceso de Firefox aparece con:

- **PID:** (ejemplo: 4321)
- **Usuario:** tu_usuario

---

## Paso 9: Interrupción de procesos (Kill)

```bash
killall firefox
```

**Explicación Técnica:**
Se usa `killall` porque permite matar todos los procesos con ese nombre sin buscar PID manualmente.

Por defecto, envía la señal **SIGTERM (15)**, que solicita al proceso terminar de forma controlada.

---

## Paso 10: Ejecución en Background con retorno de estado

```bash
firefox &
```

**Salida del sistema:**
Ejemplo:

```
[1] 5678
```

- **Job ID:** `[1]`
- **PID:** `5678`

---

## Paso 11: Listado general de procesos (Root)

```bash
ps aux
```

**Observación:**

- `a`: muestra procesos de todos los usuarios
- `u`: formato detallado (usuario, CPU, memoria)
- `x`: incluye procesos sin terminal

---

## Paso 12: Listado de trabajos por terminal

```bash
jobs
```

**Explicación:**

- `jobs` → solo muestra procesos asociados a la terminal actual
- `ps` → consulta todos los procesos del sistema operativo

---

## Paso 13: Temporizadores en primer plano

```bash
sleep 600 && pwd
```

---

## Paso 14: Comportamiento de interrupción

```bash
Ctrl + C
```

**Observación:**
Se envía la señal **SIGINT**, que interrumpe el proceso en ejecución y devuelve el control a la terminal inmediatamente.

---

## Paso 15: Retardos de interfaz gráfica

```bash
sleep 15 && libreoffice
```

---

## Paso 16: Monitorización avanzada

```bash
htop
```

**Explicación:**
Ventajas frente a `top`:

- Interfaz más visual e intuitiva (colores, barras de uso)
- Permite interactuar (matar procesos con teclas, ordenar dinámicamente)

---

## Paso 17: Terminación de procesos por usuario

```bash
pkill -u usuario
```

**Explicación Técnica:**
Matar todos los procesos de un usuario puede:

- Cerrar sesiones activas abruptamente
- Provocar pérdida de datos
- Afectar servicios críticos si ese usuario ejecuta procesos importantes

---

## Paso 18: Orquestación múltiple (Usuario estándar)

```bash
sleep 5 && pwd &
sleep 10 && ls -l &
sleep 20 && libreoffice &
```

---

## Paso 19: Orquestación múltiple (Root)

```bash
sleep 10 && ls -a &
sleep 10 && nano /etc/passwd &
sleep 20 && libreoffice &
sleep 50 && firefox &
```

---

## Paso 20: Verificación de orquestación

```bash
ps aux
```

---

## Paso 21: Caza específica (Targeted Kill)

```bash
pkill -f "ls -l"
```

---

## Paso 22: Terminación por coincidencia de nombre

```bash
killall nano
```

---

## Paso 23: Limpieza de sesión

```bash
pkill -u usuario
```

---

## Paso 24: Auditoría de estados

```bash
ps aux
```

**Explicación:**

- `R` → Running (en ejecución)
- `S` → Sleeping (esperando)
- `Z` → Zombie (proceso terminado pero no recogido por su padre)

---

## Paso 25: Repetición de orquestación en background

```bash
sleep 20 && pwd &
sleep 15 && nano /etc/passwd &
sleep 20 && libreoffice &
sleep 50 && firefox &
```

---

## Paso 26: Comprobación de Jobs

```bash
jobs
```

---

## Paso 27: Ejecución restrictiva

```bash
sleep 20 && pwd
sleep 15 && nano /etc/passwd
sleep 20 && libreoffice
sleep 50 && firefox
```

**Observación:**
En primer plano, cada comando bloquea la terminal hasta terminar, impidiendo ejecutar otros procesos simultáneamente, lo que reduce la productividad.

---

## Paso 28: Traslado de Background a Foreground

```bash
fg %1
fg %2
fg %3
```

**Explicación:**
Se usa el Job ID (`%n`) para traer procesos específicos al primer plano.

---

## Paso 29: Verificación de traslado

```bash
jobs
```

---

## Paso 30: Cierre y limpieza final

```bash
pkill -u usuario
pkill -u root
```

> **Nota de Seguridad:**
> Matar todos los procesos de root en un sistema real puede:
> 
> - Detener servicios críticos del sistema
> - Provocar inestabilidad grave
> - En casos extremos, causar un Kernel Panic o apagado del sistema
> 
> Esto ocurre porque muchos procesos esenciales (init/systemd, daemons) se ejecutan como root.


⭐ **Práctica documentada por David Valadés Navarro**