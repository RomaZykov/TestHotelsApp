import com.example.testHotelsApp.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "hotels.android.library")
            apply(plugin = "hotels.hilt")
            apply(plugin = "org.jetbrains.kotlin.plugin.serialization")

            dependencies {
                add("implementation", libs.findLibrary("kotlinx.serialization.json").get())
                add("implementation", libs.findLibrary("compose.navigation").get())
            }
        }
    }
}