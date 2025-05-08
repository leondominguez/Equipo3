# 🐶 DogApp

DogApp es una aplicación móvil desarrollada en **Kotlin** con **Android Studio**, basada en la arquitectura **MVVM**. Su objetivo es proporcionar una experiencia interactiva para gestionar información sobre perros, integrando autenticación biométrica y una interfaz moderna.

## 🚀 Características
- **Arquitectura MVVM** con separación clara de responsabilidades.
- **Autenticación biométrica** mediante `BiometricPrompt`.
- **Interfaz moderna** con Material 3 y animaciones de LottieFiles.
- **Gestión de datos** con **Retrofit** para API y **Room** para almacenamiento local.
- **Sistema de navegación** optimizado con Android Navigation Component.

## 📂 Estructura del Proyecto
La estructura del proyecto sigue una arquitectura MVVM (Model-View-ViewModel) para garantizar una separación clara de responsabilidades. A continuación, se describe la organización principal:

- **app/**: Contiene el código fuente de la aplicación.
  - **data/**: Maneja la gestión de datos, incluyendo repositorios, fuentes de datos remotas (Retrofit) y locales (Room).
  - **domain/**: Define las entidades y casos de uso de la aplicación.
  - **ui/**: Contiene las actividades, fragmentos y componentes de la interfaz de usuario.
  - **viewmodel/**: Implementa la lógica de presentación y conecta la interfaz de usuario con el dominio.
- **res/**: Incluye recursos como layouts, strings, drawables y temas.
- **build.gradle**: Archivo de configuración para la compilación del proyecto.
- **AndroidManifest.xml**: Archivo de manifiesto que define la configuración general de la aplicación.