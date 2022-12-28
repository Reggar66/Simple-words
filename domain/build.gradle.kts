plugins {
    id("com.simplewords.android.library")
    id("com.simplewords.hilt")
}

android {
    namespace = "com.ada.domain"
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":data"))

    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.database.ktx)
}