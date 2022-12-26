plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.ada.quiz"
    compileSdk = com.ada.ProjectConfig.compileSdk

    defaultConfig {
        minSdk = com.ada.ProjectConfig.minSdk
        targetSdk = com.ada.ProjectConfig.targetSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        kotlinCompilerExtensionVersion = com.ada.Dependencies.Compose.compilerVersion
    }
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:ui"))
    implementation(project(":data"))
    implementation(project(":domain"))

    implementation(project(":feature:quizdetails"))

    implementation(com.ada.Dependencies.Compose.ui)
    implementation(com.ada.Dependencies.Compose.uiToolingPreview)
    implementation(com.ada.Dependencies.Compose.material)

    implementation(com.ada.Dependencies.Hilt.android)
    implementation(com.ada.Dependencies.Hilt.navigationCompose)
    kapt(com.ada.Dependencies.Hilt.compiler)

    testImplementation(com.ada.Dependencies.jUnit)
    androidTestImplementation(com.ada.Dependencies.androidx_junit)
    androidTestImplementation(com.ada.Dependencies.androidx_espresso)

    androidTestImplementation(com.ada.Dependencies.Compose.Test.uiTestJunit)
    debugImplementation(com.ada.Dependencies.Compose.Debug.uiTooling)
    debugImplementation(com.ada.Dependencies.Compose.Debug.uiTestManifest)
}