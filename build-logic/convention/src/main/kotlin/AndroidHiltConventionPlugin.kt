import com.ada.simplewords.helper.getLibs
import com.ada.simplewords.helper.implementation
import com.ada.simplewords.helper.kapt
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies


/**
 * Gradle plugin that configures Hilt.
 */
class AndroidHiltConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.kapt")
                apply("dagger.hilt.android.plugin")
            }

            val libs = extensions.getLibs()
            dependencies {
                implementation(libs.findLibrary("hilt-android").get())
                implementation(libs.findLibrary("androidx-hilt-navigation-compose").get())
                kapt(libs.findLibrary("hilt-android-compiler").get())
            }
        }
    }
}