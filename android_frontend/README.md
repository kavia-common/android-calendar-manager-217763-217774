# Android Calendar Manager - Android Frontend

This is a multi-module Android project (Kotlin, no Compose) consisting of:
- app: Application module (namespace `org.example.app`)
- list: Simple Kotlin/Android library
- utilities: Library that depends on `:list`

The project uses the Kotlin DSL (build.gradle.kts) with Android Gradle Plugin 8.5.2 and Kotlin 1.9.24.
A standard Gradle wrapper is provided.

## Build

From the `android_frontend` directory:

```sh
./gradlew :app:assembleDebug
```

Or build everything:

```sh
./gradlew build
```

## Run

Install and run on a connected/emulator device:

```sh
./gradlew :app:installDebug
```

The app name is "Calendar Manager". It uses standard Android Views (no Compose).

## Notes

- Minimum SDK 24, target/compile SDK 34.
- ViewBinding is enabled in the app module.
- Networking uses Retrofit 2.9.0 with Moshi and OkHttp 4.12.0.
- The backend base URL defaults to `http://10.0.2.2:3001/` for Android emulator; adjust in `app/src/main/res/values/strings.xml` as needed.
