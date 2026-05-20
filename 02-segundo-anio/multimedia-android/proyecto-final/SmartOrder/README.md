&lt;div align="center"&gt;

&lt;h1&gt;📱 SmartOrder&lt;/h1&gt;
&lt;p&gt;&lt;strong&gt;Plataforma de Pedidos y Gestión Inteligente B2B/B2C&lt;/strong&gt;&lt;/p&gt;

&lt;p&gt;
&lt;img src="https://img.shields.io/badge/Estado-Completado_🚀-2ea44f?style=flat-square" alt="Status"/&gt;
&lt;img src="https://img.shields.io/badge/Android_Studio-3DDC84?style=flat-square&logo=android-studio&logoColor=white" alt="Android"/&gt;
&lt;img src="https://img.shields.io/badge/Java-ED8B00?style=flat-square&logo=openjdk&logoColor=white" alt="Java"/&gt;
&lt;img src="https://img.shields.io/badge/Supabase-3ECF8E?style=flat-square&logo=supabase&logoColor=white" alt="Supabase"/&gt;
&lt;/p&gt;

&lt;p&gt;
&lt;img src="https://img.shields.io/badge/Arquitectura-Multitenant-blueviolet?style=flat-square" alt="Architecture"/&gt;
&lt;img src="https://img.shields.io/badge/Patrón-MVVM-ff69b4?style=flat-square" alt="Pattern"/&gt;
&lt;img src="https://img.shields.io/badge/API-REST%20%2B%20Volley-orange?style=flat-square" alt="API"/&gt;
&lt;/p&gt;

&lt;/div&gt;

---

## 📋 Tabla de Contenidos

- [📋 Tabla de Contenidos](#-tabla-de-contenidos)
- [🎯 Descripción General](#-descripción-general)
- [🏗️ Arquitectura del Sistema](#️-arquitectura-del-sistema)
- [🚀 Características y Retos Técnicos Superados](#-características-y-retos-técnicos-superados)
- [👨‍💻 Autor](#-autor)

---

## 🎯 Descripción General

SmartOrder es una solución tecnológica integral diseñada para digitalizar y simplificar el proceso de pedidos y cobro en restaurantes. Elimina los cuellos de botella operativos otorgando total autonomía al comensal mediante un ecosistema móvil y web.

---

## 🏗️ Arquitectura del Sistema

El proyecto se divide en una plataforma dual sobre una arquitectura **Multitenant**, garantizando que la lógica y los datos de cada negocio operen en entornos aislados y seguros.

|          Módulo          |      Tecnología       | Descripción                                                                                                                                                                              |
| :----------------------: | :-------------------: | :--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
|   📲 **App Móvil B2C**   | Android Nativo (Java) | Interfaz ágil para el cliente final. Permite escanear un código QR en la mesa, consultar la carta digital y enviar la comanda directamente a cocina mediante peticiones asíncronas HTTP. |
| 🖥️ **Web Dashboard B2B** |    React + Node.js    | Panel de administración para el restaurante. Incluye un creador interactivo de planos de sala (Drag & Drop) y un sistema Kanban para la pantalla de cocina (KDS).                        |
|   🗄️ **Base de Datos**   | Supabase / PostgreSQL | Núcleo relacional con políticas de seguridad RLS (Row Level Security) e inyección de credenciales mediante API Keys.                                                                     |

---

## 🚀 Características y Retos Técnicos Superados

&lt;div align="center"&gt;

|           Característica            |     Tecnología / Solución      |                                   Impacto                                   |
| :---------------------------------: | :----------------------------: | :-------------------------------------------------------------------------: |
| 🔍 **Lector Óptico y Contingencia** | ZXing + Trigger SQL PostgreSQL |          Lectura de QR + códigos alfanuméricos cortos alternativos          |
|     ⚡ **Consumo de API REST**      |      Volley (HTTP Async)       |             Encolado de peticiones y prevención de errores ANR              |
|   🔐 **Seguridad de Acceso RLS**    |   Headers HTTP Bearer Tokens   |             Superación de políticas RLS desde dispositivo móvil             |
|    ✨ **Experiencia de Usuario**    |   Skeleton Loader (Shimmer)    | Enmascaramiento de latencia de red y optimización del rendimiento percibido |

&lt;/div&gt;

### 🔍 Lector Óptico y Contingencia

Integración de ZXing para lectura de códigos QR e implementación de un Trigger SQL en PostgreSQL para autogenerar códigos alfanuméricos cortos como método de acceso alternativo.

### ⚡ Consumo de API REST (Volley)

Gestión de encolado de peticiones de red y ejecución asíncrona para evitar el bloqueo del hilo principal (Main UI Thread) y prevenir errores ANR.

### 🔐 Seguridad de Acceso RLS

Sobreescritura de cabeceras HTTP (Headers) para inyectar tokens Bearer y superar las políticas de seguridad a nivel de fila directamente desde el dispositivo móvil.

### ✨ Experiencia de Usuario (UI/UX)

Implementación del patrón de diseño "Skeleton Loader" (efecto Shimmer) para enmascarar la latencia de red y optimizar el rendimiento percibido.

---

## 👨‍💻 Autor

&lt;div align="center"&gt;

**[David Valadés Navarro](https://github.com/davidValades)**

\*⭐ Proyecto desarrollado para la asignatura: **Programación Multimedia y Dispositivos Móviles\***

&lt;/div&gt;

---

&lt;div align="center"&gt;
&lt;img src="https://img.shields.io/badge/Made%20with-❤️%20%26%20☕-ff6b6b?style=flat-square" alt="Made with love"/&gt;
&lt;img src="https://img.shields.io/badge/License-Academic-2ea44f?style=flat-square" alt="License"/&gt;
&lt;/div&gt;
