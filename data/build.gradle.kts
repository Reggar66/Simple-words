plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.ada.data"
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":common"))
}