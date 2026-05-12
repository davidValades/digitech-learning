# 🗄️ Práctica 06: Fundamentos de Acceso a Datos y Spring Initializr

![Status](https://img.shields.io/badge/Estado-En_Desarrollo_🚀-2ea44f?style=for-the-badge)
![Java](https://img.shields.io/badge/Java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/Spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)

**Alumno:** David Valadés Navarro  
**Módulo:** Acceso a Datos (DAM DUAL)  

---

## 1. Empaquetado del Proyecto

**Pregunta:** ¿Qué empaquetado utiliza? Explique brevemente dos formas de averiguarlo.

**Respuesta:**
El proyecto `mavenproject1` utiliza un empaquetado de tipo `[ESCRIBE_AQUÍ_SI_ES_JAR_O_WAR]`. 

He podido averiguarlo de las siguientes dos formas:
1. **Analizando el archivo de configuración:** `[EXPLICA AQUÍ cómo lo viste dentro del archivo pom.xml que venía en el proyecto descargado]`.
2. **Revisando la estructura de compilación:** `[EXPLICA AQUÍ cómo, al mirar la carpeta target/ generada tras la compilación, se puede observar el artefacto final resultante]`.

## 2. Sintaxis y Seguridad en Consultas SQL

**Pregunta:** ¿Por qué las sentencias aparecen en color verde y entre comillas? ¿Qué significan los signos de interrogación?

**Respuesta:**
* **Color verde y comillas:** `[EXPLICA AQUÍ que en Java, el texto entre comillas dobles "" representa un objeto de tipo String. El IDE colorea estas cadenas para identificar visualmente que es texto literal, el cual en este caso almacena una consulta SQL pura]`.
* **Los signos de interrogación (`?`):** `[EXPLICA AQUÍ que estos signos representan parámetros dinámicos asociados a la interfaz PreparedStatement de JDBC. Menciona por qué esto es una práctica obligatoria de Ciberseguridad para evitar vulnerabilidades de Inyección SQL (SQL Injection)]`.

## 3. Ubicación de la Librería de Conexión (Driver)

**Pregunta:** ¿Dónde está ubicada la librería que corresponde a la conexión?

**Respuesta:**
La librería (el driver JDBC necesario para conectar con la base de datos) se encuentra ubicada lógicamente y físicamente en:
1. **Lógicamente (Declaración):** `[EXPLICA AQUÍ en qué sección del archivo pom.xml se declara esta dependencia]`.
2. **Físicamente (Descarga):** `[EXPLICA AQUÍ en qué carpeta oculta de tu sistema operativo (ej. la carpeta .m2 de Maven) se descarga y almacena el archivo .jar de dicha librería]`.

## 4. Experimentación con el Proyecto

**Pregunta:** Experimente con el proyecto.

**Respuesta:**
Durante la fase de experimentación he interactuado con el archivo `App.java` y he realizado las siguientes modificaciones para comprender el comportamiento del sistema:
* `[AÑADE EXPERIMENTO 1: Ej. "Modifiqué las credenciales de conexión en el código para provocar un fallo intencionado y observé cómo se lanzaba una SQLException"]`.
* `[AÑADE EXPERIMENTO 2: Ej. "Alteré la consulta SQL modificando los signos de interrogación (?) para observar el comportamiento del PreparedStatement en tiempo de ejecución"]`.

## 5. Comparativa con Spring Initializr

**Pregunta:** Cree un proyecto con Spring Initializr, jar, con las dependencias del proyecto ejemplo y compare ambas estructuras.

**Respuesta:**
He generado un nuevo proyecto base utilizando [Spring Initializr](https://start.spring.io/) configurado con Maven, empaquetado `.jar` y las dependencias de acceso a datos correspondientes. Al comparar esta estructura moderna con el proyecto de ejemplo `mavenproject1`, destaco las siguientes diferencias arquitectónicas:

* **Gestión de Configuración:** `[MENCIONA cómo Spring Boot introduce la carpeta src/main/resources/application.properties para externalizar credenciales, en lugar de tenerlas "hardcodeadas" en la clase App.java]`.
* **Arranque de la Aplicación:** `[EXPLICA la diferencia entre el método main tradicional y el uso de la anotación @SpringBootApplication]`.
* **Wrappers de Construcción:** `[MENCIONA la presencia de archivos como mvnw (Maven Wrapper) en el proyecto Spring, los cuales permiten compilar sin tener Maven instalado globalmente en la máquina]`.

---
_⭐ Documento técnico elaborado por David Valadés Navarro._