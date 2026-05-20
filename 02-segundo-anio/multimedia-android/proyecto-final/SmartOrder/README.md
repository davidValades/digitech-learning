# 📱 SmartOrder | Plataforma de Pedidos y Gestión Inteligente B2B/B2C

![Status](https://img.shields.io/badge/Estado-Completado_🚀-2ea44f?style=for-the-badge)
![Android](https://img.shields.io/badge/Android_Studio-3DDC84?style=for-the-badge&logo=android-studio&logoColor=white)
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Supabase](https://img.shields.io/badge/Supabase-3ECF8E?style=for-the-badge&logo=supabase&logoColor=white)

[cite_start]SmartOrder es una solución tecnológica integral diseñada para digitalizar y simplificar el proceso de pedidos y cobro en restaurantes[cite: 4]. [cite_start]Elimina los cuellos de botella operativos otorgando total autonomía al comensal mediante un ecosistema móvil y web.

## 🏗️ Arquitectura del Sistema

[cite_start]El proyecto se divide en una plataforma dual sobre una arquitectura **Multitenant**, garantizando que la lógica y los datos de cada negocio operen en entornos aislados y seguros.

- **App Móvil B2C (Android Nativo):** Interfaz ágil para el cliente final. [cite_start]Permite escanear un código QR en la mesa, consultar la carta digital y enviar la comanda directamente a cocina mediante peticiones asíncronas HTTP.
- **Web Dashboard B2B (React + Node.js):** Panel de administración para el restaurante. [cite_start]Incluye un creador interactivo de planos de sala (Drag & Drop) y un sistema Kanban para la pantalla de cocina (KDS).
- [cite_start]**Base de Datos (Supabase / PostgreSQL):** Núcleo relacional con políticas de seguridad RLS (Row Level Security) e inyección de credenciales mediante API Keys.

## 🚀 Características y Retos Técnicos Superados

- [cite_start]**Lector Óptico y Contingencia:** Integración de ZXing para lectura de códigos QR e implementación de un Trigger SQL en PostgreSQL para autogenerar códigos alfanuméricos cortos como método de acceso alternativo.
- [cite_start]**Consumo de API REST (Volley):** Gestión de encolado de peticiones de red y ejecución asíncrona para evitar el bloqueo del hilo principal (Main UI Thread) y prevenir errores ANR.
- [cite_start]**Seguridad de Acceso RLS:** Sobreescritura de cabeceras HTTP (Headers) para inyectar tokens Bearer y superar las políticas de seguridad a nivel de fila directamente desde el dispositivo móvil.
- [cite_start]**Experiencia de Usuario (UI/UX):** Implementación del patrón de diseño "Skeleton Loader" (efecto Shimmer) para enmascarar la latencia de red y optimizar el rendimiento percibido.

---

[cite_start]_⭐ Proyecto desarrollado por [David Valadés Navarro](https://github.com/davidValades) - Asignatura: Programación Multimedia y Dispositivos Móviles._
