package com.ada.simplewords

import com.ada.simplewords.helper.getLibs
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

/**
 * Configures build logic for android.
 */
internal fun Project.configureAndroid(extension: CommonExtension<*, *, *, *>) {
    val libs = extensions.getLibs()
    extension.apply {
        compileSdk = libs.findVersion("compileSdk").get().requiredVersion.toInt()

        defaultConfig {
            minSdk = libs.findVersion("minSdk").get().requiredVersion.toInt()

            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }

        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_1_8.toString()
        }
    }
}

/**
 * Configures build logic specific for android library.
 */
internal fun Project.configureAndroidLibrary(extension: LibraryExtension) {
    val libs = extensions.getLibs()
    extension.apply {

        defaultConfig {
            targetSdk = libs.findVersion("targetSdk").get().requiredVersion.toInt()
            consumerProguardFile("consumer-rules.pro")
        }

        buildTypes {
            release {
                isMinifyEnabled = false

                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
        }
    }
}

fun CommonExtension<*, *, *, *>.kotlinOptions(block: KotlinJvmOptions.() -> Unit) {
    (this as ExtensionAware).extensions.configure("kotlinOptions", block)
}