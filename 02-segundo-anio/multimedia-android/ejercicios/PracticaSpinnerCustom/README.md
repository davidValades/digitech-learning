# 🌍 Custom Spinner con Imágenes (Android)

![Status](https://img.shields.io/badge/Estado-En_Progreso_🚀-2ea44f?style=for-the-badge)
![Android Studio](https://img.shields.io/badge/Android%20Studio-3DDC84?style=for-the-badge&logo=android-studio&logoColor=white)
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)

Proyecto de desarrollo Android centrado en la creación de interfaces de usuario avanzadas mediante la personalización de componentes nativos. El objetivo es implementar un `Spinner` (menú desplegable) que renderice elementos compuestos (Imágenes + Texto), superando las limitaciones del diseño por defecto.

## ✨ Funcionalidades y Retos Técnicos

- **Adaptador Personalizado (`BaseAdapter`):** Creación de una clase Java dedicada (`CountryAdapter`) que extiende de `BaseAdapter`. Esto permite definir reglas de renderizado específicas para cada fila del desplegable.
- **Inflado de Vistas (`LayoutInflater`):** Uso del servicio de inflado para convertir un archivo XML secundario (que define la estructura visual de una fila con su imagen y texto) en objetos Java manipulables en tiempo de ejecución.
- **Gestión de Recursos (`Drawables`):** Inyección dinámica de imágenes (`ImageView`) almacenadas en la carpeta `res/drawable` en función de los datos del adaptador.
- **Separación de Responsabilidades (MVC):** El `MainActivity` se encarga de instanciar los datos, el `Adapter` de dibujarlos, y el XML de definirlos visualmente.

## 📁 Arquitectura del Proyecto

Para lograr el diseño personalizado, el proyecto requiere una estructura multicapa:

1.  `activity_main.xml`: Contenedor principal que aloja el componente `Spinner`.
2.  `spinner_item_custom.xml`: Diseño individual (Layout) que define cómo se ve una sola fila del Spinner (ImageView a la izquierda, TextView a la derecha).
3.  `CountryAdapter.java`: La clase puente que conecta los datos (nombres de países e identificadores de imágenes) con el diseño personalizado (`spinner_item_custom.xml`).
4.  `MainActivity.java`: Controlador que inicializa el proceso.

## 🚀 Cómo ejecutar el proyecto

1.  Clona este repositorio.
2.  Abre el proyecto en **Android Studio**.
3.  Sincroniza el proyecto con Gradle.
4.  Ejecuta en un emulador o dispositivo físico.
