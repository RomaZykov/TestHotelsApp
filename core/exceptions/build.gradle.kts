plugins {
    alias(libs.plugins.hotels.android.library)
    alias(libs.plugins.hotels.hilt)
}

android {
    namespace = "com.example.exceptions"
}

dependencies {
    implementation(project(":core:common"))
}