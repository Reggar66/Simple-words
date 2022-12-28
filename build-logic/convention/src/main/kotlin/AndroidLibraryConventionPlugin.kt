import com.ada.simplewords.configureAndroid
import com.ada.simplewords.configureAndroidLibrary
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

/**
 * Gradle plugin that configures build logic for Android Library Module.
 */
class AndroidLibraryConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.android.library")
            pluginManager.apply("org.jetbrains.kotlin.android")

            val extension = extensions.getByType<LibraryExtension>()
            configureAndroid(extension)
            configureAndroidLibrary(extension)
        }
    }
}