# FitLife Android Native

## 🤖 Aplicación Android Nativa

Esta es la aplicación Android nativa de FitLife desarrollada con:
- **Kotlin** 100%
- **Jetpack Compose** para UI
- **Material Design 3** + **Material You**
- **Hilt** para inyección de dependencias
- **MVVM** + Repository Pattern

## 🚀 Configuración

### Prerrequisitos
- Android Studio Arctic Fox o superior
- JDK 17+
- Android SDK 34
- Gradle 8.4+

### Setup
1. Abre el proyecto en Android Studio
2. Sincroniza Gradle
3. Configura el emulador o dispositivo
4. Ejecuta la aplicación

### Configuración de API
Edita `local.properties`:
```properties
api.base.url=http://10.0.2.2:8080/api
ws.url=ws://10.0.2.2:8080/ws
```

## 🎨 Características

### Material Design 3
- ✅ Dynamic Colors (Material You)
- ✅ Adaptive layouts
- ✅ Bottom Navigation
- ✅ Material Cards
- ✅ Ripple effects

### Funcionalidades
- 🔐 Autenticación JWT
- 👤 Gestión de perfil
- 💪 Ejercicios y rutinas
- 🥗 Seguimiento nutricional
- 👥 Red social
- 💬 Mensajería en tiempo real
- 📊 Dashboard con estadísticas

### Integraciones Android
- 📱 Biometric authentication
- 🔔 Push notifications
- 📱 Widgets de home screen
- 🔄 Share intents
- 📊 WorkManager para tareas en background

## 🏗️ Arquitectura

```
app/
├── src/main/java/com/fitlife/android/
│   ├── MainActivity.kt
│   ├── FitLifeApplication.kt
│   ├── ui/
│   │   ├── theme/
│   │   └── screens/
│   ├── data/
│   │   ├── model/
│   │   ├── repository/
│   │   └── api/
│   ├── navigation/
│   └── viewmodel/
└── src/main/res/
    ├── values/
    ├── drawable/
    └── mipmap-*/
```

## 🧪 Testing

```bash
# Unit tests
./gradlew test

# Instrumented tests
./gradlew connectedAndroidTest

# Build debug APK
./gradlew assembleDebug
```

## 📦 Build

```bash
# Debug build
./gradlew assembleDebug

# Release build
./gradlew assembleRelease

# Bundle for Play Store
./gradlew bundleRelease
```

## 🚀 Deployment

1. **Debug**: Instalar APK directamente
2. **Release**: Subir bundle a Google Play Console
3. **CI/CD**: GitHub Actions configurado

## 📱 Características Específicas de Android

### Material You
- Colores dinámicos del sistema (Android 12+)
- Adaptación automática al wallpaper
- Temas claro/oscuro

### Widgets
- Widget de progreso diario
- Widget de estadísticas rápidas
- Accesos directos a rutinas

### Notificaciones
- Push notifications para recordatorios
- Notificaciones de progreso
- Notificaciones sociales (likes, mensajes)

### Biometría
- Face unlock
- Fingerprint authentication
- Patrón/PIN como fallback

## 🔧 Troubleshooting

### Problemas Comunes

1. **Gradle sync failed**
   - Verificar versión de JDK (17+)
   - Limpiar cache: `./gradlew clean`

2. **API connection issues**
   - Verificar `local.properties`
   - Para emulador usar `10.0.2.2` en lugar de `localhost`

3. **Build errors**
   - Verificar Android SDK instalado
   - Sincronizar proyecto con Gradle

### Logs
```bash
# Ver logs de la aplicación
adb logcat | grep FitLife

# Ver logs de red
adb logcat | grep OkHttp
```

## 📚 Recursos

- [Material Design 3](https://m3.material.io/)
- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Android Architecture Guide](https://developer.android.com/topic/architecture)
- [Hilt Documentation](https://dagger.dev/hilt/)