package com.ada

object Dependencies {

    const val jUnit = "junit:junit:4.13.2"
    const val androidx_junit = "androidx.test.ext:junit:1.1.3"
    const val androidx_espresso = "androidx.test.espresso:espresso-core:3.4.0"

    const val core = "androidx.core:core-ktx:1.9.0"
    const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:2.5.1"
    const val activityCompose = "androidx.activity:activity-compose:1.5.1"

    const val navigation = "androidx.navigation:navigation-compose:2.5.2"

    object Compose {
        const val compilerVersion = "1.3.1"
        private const val version = "1.2.1"

        const val ui = "androidx.compose.ui:ui:$version"
        const val uiToolingPreview = "androidx.compose.ui:ui-tooling-preview:$version"
        const val material = "androidx.compose.material:material:$version"

        object Test {
            const val uiTestJunit = "androidx.compose.ui:ui-test-junit4:$version"
        }

        object Debug {
            const val uiTooling = "androidx.compose.ui:ui-tooling:$version"
            const val uiTestManifest = "androidx.compose.ui:ui-test-manifest:$version"
        }
    }

    object Hilt {
        private const val version = "2.44"

        const val android = "com.google.dagger:hilt-android:$version"
        const val compiler = "com.google.dagger:hilt-android-compiler:$version"
        const val navigationCompose = "androidx.hilt:hilt-navigation-compose:1.0.0"
    }

    object Firebase {
        const val bom = "com.google.firebase:firebase-bom:31.1.0"
        const val realTimeDatabase = "com.google.firebase:firebase-database-ktx"
    }
}