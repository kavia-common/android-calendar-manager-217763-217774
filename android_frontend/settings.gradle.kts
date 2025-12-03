pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    plugins {
        // Pin Android Gradle Plugin and Kotlin versions
        id("com.android.application") version "8.5.2"
        id("com.android.library") version "8.5.2"
        kotlin("android") version "1.9.24"
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "AndroidCalendarManager"

include(":app")
include(":list")
include(":utilities")
