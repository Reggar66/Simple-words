import com.example.Dependencies
import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.simplewords"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.simplewords"
        minSdk = 27
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Dependencies.Compose.compilerVersion
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(Dependencies.core)
    implementation(Dependencies.lifecycleRuntime)
    implementation(Dependencies.activityCompose)

    implementation(Dependencies.Compose.ui)
    implementation(Dependencies.Compose.uiToolingPreview)
    implementation(Dependencies.Compose.material)

    implementation(Dependencies.navigation)

    implementation(Dependencies.Hilt.android)
    implementation(Dependencies.Hilt.navigationCompose)
    kapt(Dependencies.Hilt.compiler)

    testImplementation(Dependencies.jUnit)
    androidTestImplementation(Dependencies.androidx_junit)
    androidTestImplementation(Dependencies.androidx_espresso)

    androidTestImplementation(Dependencies.Compose.Test.uiTestJunit)
    debugImplementation(Dependencies.Compose.Debug.uiTooling)
    debugImplementation(Dependencies.Compose.Debug.uiTestManifest)
}