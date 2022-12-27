plugins {
    `kotlin-dsl`
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(libs.android.gradlePlugin)
    implementation(libs.kotlin.gradlePlugin)
    //implementation("com.android.tools.build:gradle:7.3.1")
    //implementation("org.jetbrains.kotlin.android:org.jetbrains.kotlin.android.gradle.plugin:1.7.10")
}