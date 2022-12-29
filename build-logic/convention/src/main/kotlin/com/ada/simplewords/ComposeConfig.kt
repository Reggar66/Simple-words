package com.ada.simplewords

import com.ada.simplewords.helper.androidTestImplementation
import com.ada.simplewords.helper.debugImplementation
import com.ada.simplewords.helper.getLibs
import com.ada.simplewords.helper.implementation
import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies


/**
 * Configures build logic with compose features for android.
 */
internal fun Project.configureCompose(extension: CommonExtension<*, *, *, *>) {
    val libs = extensions.getLibs()

    extension.apply {
        buildFeatures {
            compose = true
        }
        composeOptions {
            kotlinCompilerExtensionVersion = libs.findVersion("composeCompilerVersion")
                .get().requiredVersion // required is default value that is parsed.
        }
        packagingOptions {
            resources {
                excludes += "/META-INF/{AL2.0,LGPL2.1}"
            }
        }
    }

    dependencies {
        implementation(libs.findLibrary("androidx-compose-ui").get())
        implementation(libs.findLibrary("androidx-compose-ui-tooling-preview").get())
        implementation(libs.findLibrary("androidx-compose-material").get())

        androidTestImplementation(libs.findLibrary("androidx-compose-ui-test-junit4").get())
        debugImplementation(libs.findLibrary("androidx-compose-ui-tooling").get())
        debugImplementation(libs.findLibrary("androidx-compose-ui-test-manifest").get())
    }
}
