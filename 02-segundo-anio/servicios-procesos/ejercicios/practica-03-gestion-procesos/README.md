# 🐧 Práctica: Procesos III (Gestión Multi-Usuario y Orquestación)

![Status](https://img.shields.io/badge/Estado-Completado_✅-2ea44f?style=for-the-badge)
![Linux](https://img.shields.io/badge/Linux-FCC624?style=for-the-badge&logo=linux&logoColor=black)
![Bash](https://img.shields.io/badge/Bash-4EAA25?style=for-the-badge&logo=gnu-bash&logoColor=white)

Este documento sirve como cuaderno de laboratorio para la práctica "Práctica procesos III", profundizando en la gestión de permisos multi-usuario, el control de trabajos (Jobs) y la orquestación mediante temporizadores (`sleep`).

---

## 🎯 Objetivo de la Práctica

- Creación y gestión de usuarios con y sin privilegios de superusuario (`sudo`).
- Aislamiento de procesos por usuario y sesión de terminal.
- Manipulación avanzada del ciclo de vida de los procesos (`fg`, `bg`, `kill`, `pkill`).
- Orquestación de procesos en segundo plano con retardos temporales.

---

## 📝 Desarrollo de la Práctica (Paso a Paso)

---

### Paso 1: Creación del usuario profesor

> **Requerimiento:** Crea el usuario profesor, conecta con este usuario, para que se cree su directorio personal.

```bash
sudo useradd -m profesor
sudo passwd profesor
su - profesor
```

**Explicación:** El flag `-m` en `useradd` es crucial, ya que fuerza la creación del directorio `/home/profesor` (Home Directory). El comando `su -` (con el guion) nos conecta simulando un inicio de sesión completo, cargando las variables de entorno de ese usuario.

---

### Paso 2: Creación del usuario tutor

> **Requerimiento:** Crea el usuario tutor, conecta con este usuario, para que se cree su directorio personal.

```bash
sudo useradd -m tutor
sudo passwd tutor
su - tutor
```

---

### Paso 3: Creación de usuario administrador (director)

> **Requerimiento:** Ahora agrega un usuario con el nombre, director. Este usuario tiene que tener privilegios para poder realizar sudo.

```bash
sudo useradd -m director
sudo passwd director
sudo usermod -aG sudo director
```

**Explicación:** Se añade el usuario director al grupo `sudo` utilizando `usermod -aG` (Append Group). Esto le otorga la capacidad de elevar privilegios temporalmente.

---

### Paso 4: Preparación del entorno

> **Requerimiento:** Abre tres terminales, una por cada usuario, vamos a trabajar con procesos.

```bash
# Terminal 1: su - profesor
# Terminal 2: su - tutor
# Terminal 3: su - director
```

---

### Paso 5: Proceso en background (Profesor)

> **Requerimiento:** Con el usuario de profesor, lanza la aplicación de Chrome. Envía este proceso al segundo plano.

```bash
google-chrome &
```

> **Nota de entorno:** Dependiendo de la distribución, el comando puede ser `google-chrome`, `google-chrome-stable` o `chromium-browser`.

---

### Paso 6: Proceso en background (Tutor)

> **Requerimiento:** Con el usuario de tutor, lanza una aplicación de libreoffice, la que prefieras. Envía este proceso al segundo plano.

```bash
libreoffice &
```

---

### Paso 7: Monitorización (Director)

> **Requerimiento:** Con el usuario director, lanza la herramienta top, para ver los procesos que está ejecutándose.

```bash
sudo top
```

**Explicación:** Al ser ejecutado con `sudo`, el usuario director puede ver la tabla completa de procesos del sistema de todos los usuarios.

---

### Paso 8: Gestión de Jobs (Profesor)

> **Requerimiento:** Con el usuario profesor, lanza el navegador Firefox en segundo plano. Restaura el proceso del punto 5 al primer plano.

```bash
firefox &
jobs
fg 1
```

**Explicación:** Tras lanzar Firefox, comprobamos los trabajos con `jobs`. Asumiendo que Chrome era el trabajo `[1]`, usamos `fg 1` para traerlo de vuelta al foreground (primer plano), bloqueando nuevamente la terminal del profesor.

---

### Paso 9: Ejecución de software (Tutor)

> **Requerimiento:** Con el usuario tutor, ejecuta firefox. Si no está instalada, instálala. Envía este proceso al segundo plano.

```bash
sudo apt install firefox
firefox &
```

---

### Paso 10 y 11: Intervención administrativa (Director)

> **Requerimiento:** Con director, abre una herramienta de monitorización. Restaura Firefox al primer plano y mátalo.

```bash
sudo htop
# Cierre de proceso:
sudo pkill -f firefox
```

**Aclaración Técnica:** Un usuario (incluso siendo sudo) no puede usar el comando `fg` para traer al primer plano un proceso iniciado en la sesión/terminal de otro usuario, ya que los Jobs están atados a la sesión de shell interactiva. Por ello, la terminación se realiza localizando el proceso y enviando la señal de interrupción directamente con `pkill` o desde `htop` (F9).

---

### Paso 12: Auditoría de usuario

> **Requerimiento:** Visualiza los procesos de tutor.

```bash
ps -u tutor
```

---

### Paso 13 y 14: Terminación masiva

> **Requerimiento:** Mata todos los procesos de libreoffice. Finaliza todos los procesos que ha abierto el usuario tutor.

```bash
sudo killall libreoffice
sudo pkill -u tutor
```

---

### Paso 15: Verificación

> **Requerimiento:** Comprueba con monitorización que profesor y tutor ya no ejecutan esos procesos.

```bash
sudo top -u tutor
sudo top -u profesor
```

---

### Paso 16: Bucle temporizado

> **Requerimiento:** Lanza un proceso pwd, con el usuario root cada 2 segundos, tres veces consecutivas.

```bash
sudo su
for i in 1 2 3; do sleep 2 && pwd; done
```

**Explicación:** En lugar de encadenar `sleep 2 && pwd && sleep 2...`, utilizamos un bucle `for` de Bash para crear una ejecución limpia y programática.

---

### Paso 17: Retardos de aplicaciones servidor

> **Requerimiento:** Conecta con tu usuario de instalación y ejecuta xampp con un tiempo de demora de 4 segundos.

```bash
(sleep 4 && sudo /opt/lampp/manager-linux-x64.run) &
```

> **Nota:** Se utiliza la ruta estándar de instalación de XAMPP en sistemas Linux. Se encapsula en `()` para no bloquear la terminal durante la espera.

---

### Paso 18: Orquestación múltiple asíncrona

> **Requerimiento:** Lanza con tu usuario de instalación: dos `ls -l` con demora de 3 sg y un `htop` con demora de 5 segundos.

```bash
(sleep 3 && ls -l) & (sleep 3 && ls -l) & (sleep 5 && htop) &
```

---

### Paso 19: Orquestación secuencial (Tutor)

> **Requerimiento:** Con tutor, lanza la calculadora con demora de 2 sg y luego un `ls -l` con demora de 4 sg.

```bash
(sleep 2 && gnome-calculator && sleep 4 && ls -l) &
```

**Explicación:** Al encadenarlos con `&&` dentro del mismo bloque, el `ls -l` no empezará a contar sus 4 segundos hasta que la calculadora se haya abierto.

---

### Paso 20: Listado de Jobs

> **Requerimiento:** Lanza con tutor tres procesos en segundo plano y lístalos.

```bash
gedit &
firefox &
libreoffice &
jobs
```

---

### Paso 21: Limpieza de Tutor

> **Requerimiento:** Mata todos los procesos del usuario tutor.

```bash
sudo pkill -u tutor
```

---

### Paso 22 y 23: Bloque de comandos temporizados (Profesor)

> **Requerimiento:** Con profesor, lanza procesos con diferentes retardos (Firefox 4s, `ls -l` 4s, `htop` 6s, `ls -a` 1s) y comprueba.

```bash
(sleep 4 && firefox) &
(sleep 4 && ls -l) &
(sleep 6 && htop) &
(sleep 1 && ls -a) &
jobs
```

---

### Paso 24: Limpieza de Profesor

> **Requerimiento:** Mata todos los procesos del usuario profesor.

```bash
sudo pkill -u profesor
```

---

### Paso 25: Caza manual por PID

> **Requerimiento:** ¿Hay algún proceso del usuario de instalación activo? ¿Cuántos?. Mata 2 de ellos.

```bash
# 1. Comprobamos los procesos de nuestro usuario:
ps -u <tu_usuario_instalacion> | wc -l

# 2. Vemos sus PIDs específicos:
ps -u <tu_usuario_instalacion>

# 3. Matamos dos de ellos usando su ID (sustituyendo PID1 y PID2 por los números reales):
kill -9 <PID1> <PID2>
```

**Explicación:** Utilizamos `wc -l` (Word Count - Lines) concatenado mediante una tubería (`|`) para contar exactamente cuántos procesos tiene activos el usuario. Luego utilizamos `kill -9` (SIGKILL) contra dos IDs específicos.

---

⭐ **Práctica documentada por David Valadés Navarro**