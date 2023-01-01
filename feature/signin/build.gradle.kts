plugins {
    id("com.simplewords.android.library.compose")
    id("com.simplewords.hilt")
}

android {
    namespace = "com.ada.signin"
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:ui"))
    implementation(project(":data"))
    implementation(project(":domain"))

    implementation(libs.androidx.core.ktx)

    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.androidx.test.esspresso.core)
}