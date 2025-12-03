plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "org.example.app"
    compileSdk = 34

    defaultConfig {
        applicationId = "org.example.app"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            // keep default
        }
    }

    // Ensure unit test task doesn't fail build when no tests are discovered
    testOptions {
        // Also enable unit tests to return default values for Android framework methods if used in tests
        unitTests.isReturnDefaultValues = true
    }

    // Enable view binding for safer view lookups (still compatible with non-Compose)
    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

tasks.withType<Test>().configureEach {
    // PUBLIC_INTERFACE
    // Configure unit tests; Gradle/AGP does not support failOnNoTests for Android unit tests.
    // Keeping block for future per-test configuration if needed.
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom:1.9.24"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.24")

    implementation(project(":utilities"))

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.viewpager2:viewpager2:1.1.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.coordinatorlayout:coordinatorlayout:1.2.0")
    implementation("androidx.fragment:fragment-ktx:1.8.4")

    // Networking (Moshi converter as used in code)
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // Unit testing
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.2")
    testImplementation("io.mockk:mockk:1.13.12")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.1")

    // Android instrumented tests
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
}
