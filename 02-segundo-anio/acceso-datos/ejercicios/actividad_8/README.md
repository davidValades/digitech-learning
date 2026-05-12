# Tarea 8: Acceso a Base de Datos con Spring e Hibernate

## 1. Creación del proyecto

Descargamos las dependencias iniciales utilizando el generador oficial de Spring Boot (https://start.spring.io/).

**Configuración seleccionada:**

- **Project:** Maven
- **Language:** Java
- **Spring Boot:** 4.0.6
- **Java:** 21

**Project Metadata:**

- **Group:** `davidvalades`
- **Artifact:** `ejercicio8`
- **Package name:** `davidvalades.ejercicio8`
- **Packaging:** Jar

**Dependencies:**

- Spring Web
- Thymeleaf
- Spring Data JPA
- MySQL Driver
- Spring Boot DevTools

Descargamos el archivo `.ZIP` generado y lo descomprimimos en nuestro entorno local.

---

## 2. Preparación en el IDE

1.  Abrimos nuestro IDE (IntelliJ IDEA) y seleccionamos `File > Open...`.
2.  Elegimos la carpeta descomprimida `ejercicio8` [Ver Foto 1](1.png)
3.  Esperamos a que IntelliJ resuelva y descargue las dependencias de Spring e Hibernate a través de Maven.
4.  Comprobamos en la ruta `src/main/java/davidvalades/ejercicio8/Ejercicio8Application` que la clase principal tiene linkeada correctamente la anotación `@SpringBootApplication` [Ver Foto 2](2.png)

---

## 3. Creación del Modelo de Datos

Dentro de `davidvalades.ejercicio8` creamos un paquete nuevo llamado `model`. En su interior, creamos una nueva clase Java (`New > Java Class`) llamada `Persona`, utilizando el código facilitado en la documentación de clase adaptado a nuestro paquete [Ver Foto 3](3.png)

```java
package davidvalades.ejercicio8.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nombre;

    @Column
    private String apellido;

    @Column
    private String direccion;

    @Column
    private String telefono;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
```

---

## 4. Configuración de la Conexión (.properties)

Navegamos a la carpeta `src/main/resources` [Ver Foto 4](4.png) y configuramos el archivo `application.properties` con los datos de acceso a nuestro servidor local y las directivas de Hibernate [Ver Foto 5](5.png):

```Properties
spring.application.name=ejercicio8

# Configuración de la base de datos
spring.datasource.url=jdbc:mysql://localhost:3306/ejercicio8_db
spring.datasource.username=root
spring.datasource.password=1992
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# Configuración de Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

## 5. Preparación de la Base de Datos

En nuestro cliente MySQL Workbench, abrimos una nueva consulta (Query) y ejecutamos la siguiente sentencia DDL para crear el esquema al que se conectará nuestra aplicación Spring Boot:

```sql
CREATE DATABASE ejercicio8_db;
```

[Ver Foto 6](6.png)

---

## 6. Preguntas Teóricas de la Tarea

### Diferencia entre javax y jakarta:

La principal diferencia reside en un cambio de propiedad legal y derechos de marca. Las tecnologías de Java Enterprise Edition pertenecían originalmente a Oracle y utilizaban el espacio de nombres `javax.*`. Cuando Oracle donó la plataforma a Eclipse, los derechos de la marca "Java" no fueron cedidos. Como resultado, las versiones modernas utilizan "Jakarta EE", obligando a utilizar en los paquetes el renombrado bajo `jakarta.*`.

### Explicación de las propiedades de configuración:

- `spring.jpa.hibernate.ddl-auto=update`: Da las instrucciones a Hibernate para que gestione automáticamente la estructura del esquema de la base de datos. Al arrancar la aplicación, si detecta diferencias entre las clases del modelo y la base de datos, actualiza el esquema creando o añadiendo tablas o columnas nuevas, evitando destruir los datos previamente almacenados.
- `spring.jpa.show-sql=true`: Activa el registro en la consola del entorno de desarrollo de todas las sentencias SQL que Hibernate genera y ejecuta en segundo plano. Es una herramienta imprescindible en la depuración para observar cómo las operaciones de los objetos Java se traducen en consultas a la base de datos.
