androidApplication {
    namespace = "org.example.app"

    dependencies {
        implementation("org.apache.commons:commons-text:1.11.0")
        implementation(project(":utilities"))

        // AndroidX and Material (no Compose)
        implementation("androidx.appcompat:appcompat:1.7.0")
        implementation("com.google.android.material:material:1.12.0")
        implementation("androidx.recyclerview:recyclerview:1.3.2")
        implementation("androidx.viewpager2:viewpager2:1.1.0")
        implementation("androidx.constraintlayout:constraintlayout:2.1.4")
        implementation("androidx.coordinatorlayout:coordinatorlayout:1.2.0")
        implementation("androidx.fragment:fragment-ktx:1.8.4")

        // Networking: Retrofit + Moshi + OkHttp
        implementation("com.squareup.retrofit2:retrofit:2.9.0")
        implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
        implementation("com.squareup.okhttp3:okhttp:4.12.0")
        implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
    }
}
