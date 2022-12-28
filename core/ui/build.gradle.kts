plugins {
    id("com.simplewords.android.library.compose")
}

android {
    namespace = "com.ada.ui"
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":data"))
    implementation(project(":domain"))

    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.androidx.test.esspresso.core)
}