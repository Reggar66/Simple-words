plugins {
    id("com.simplewords.android.library")
    id("com.simplewords.hilt")
}

android {
    namespace = "com.ada.data"
}

dependencies {
    implementation(project(":core:common"))

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.database.ktx)
}