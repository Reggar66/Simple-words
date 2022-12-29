import com.ada.simplewords.configureAndroid
import com.ada.simplewords.configureAndroidLibrary
import com.ada.simplewords.configureCompose
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.getByType

/**
 * Gradle plugin that configures build logic with compose features enabled for Android Library Module.
 */
class AndroidLibraryComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.android.library")
            pluginManager.apply("org.jetbrains.kotlin.android")

            val extension = extensions.getByType<LibraryExtension>()
            configureAndroid(extension)
            configureAndroidLibrary(extension)
            configureCompose(extension)
        }
    }
}