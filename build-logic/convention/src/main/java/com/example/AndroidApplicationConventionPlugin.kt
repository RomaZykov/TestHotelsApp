import com.android.build.api.dsl.ApplicationExtension
import com.example.testHotelsApp.configureKotlin
import com.example.testHotelsApp.configureKotlinAndroid
import com.example.testHotelsApp.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.configure

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "com.android.application")
            apply(plugin = "org.jetbrains.kotlin.android")

            extensions.configure<ApplicationExtension> {
                defaultConfig.targetSdk = libs.findVersion("target-sdk").get().toString().toInt()
                buildFeatures.buildConfig = true

                configureKotlin()
                configureKotlinAndroid(this)
            }
        }
    }
}