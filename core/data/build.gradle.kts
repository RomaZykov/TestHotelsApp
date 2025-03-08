plugins {
    alias(libs.plugins.hotels.android.library)
    alias(libs.plugins.hotels.hilt)
}

android {
    namespace = "com.example.data"
}

dependencies {

    implementation(project(":core:domain"))
    implementation(project(":core:network"))
    implementation(project(":core:exceptions"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}