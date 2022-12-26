plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.quizdetails"
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

    implementation(com.ada.Dependencies.Compose.ui)
    implementation(com.ada.Dependencies.Compose.uiToolingPreview)
    implementation(com.ada.Dependencies.Compose.material)

    implementation(com.ada.Dependencies.Hilt.android)
    implementation(com.ada.Dependencies.Hilt.navigationCompose)
    kapt(com.ada.Dependencies.Hilt.compiler)

    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.5.1")
    implementation("com.google.android.material:material:1.7.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.4")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.0")
}