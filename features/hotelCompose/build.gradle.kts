plugins {
    alias(libs.plugins.hotels.android.feature)
    alias(libs.plugins.hotels.android.library.compose)
    alias(libs.plugins.hotels.hilt)
}

android {
    namespace = "com.example.hotelcompose"
}

dependencies {

    implementation(project(":core:domain"))
    implementation(project(":core:navigation"))
    implementation(project(":core:theme"))
    implementation(project(":core:ui"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.compose.ui.android)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material3.android)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // Glide
    implementation(libs.glide)
    implementation(libs.glide.compose)

    debugImplementation(libs.compose.ui.tooling)

    testImplementation(libs.junit)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}