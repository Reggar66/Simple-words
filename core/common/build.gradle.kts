plugins {
    id("com.simplewords.android.library.compose")
}

android {
    namespace = "com.ada.common"
}

dependencies {

    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.androidx.test.esspresso.core)
}