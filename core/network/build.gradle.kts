plugins {
    alias(libs.plugins.hotels.android.library)
    alias(libs.plugins.hotels.hilt)
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "com.example.network"
}

dependencies {

    implementation(project(":core:exceptions"))

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
}