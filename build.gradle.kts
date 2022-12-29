buildscript {
    extra.apply {
        //set("compose_ui_version", "1.1.1")
    }

    dependencies {
        classpath("com.google.gms:google-services:4.3.14")
    }
} // Top-level build file where you can add configuration options common to all sub-projects/modules.

// TODO: Remove suppress annotations once #22797 is fixed.
// Issue links for reference:
// https://youtrack.jetbrains.com/issue/KTIJ-19369
// https://github.com/gradle/gradle/issues/22797
plugins {
    @Suppress("DSL_SCOPE_VIOLATION")
    alias(libs.plugins.android.application) apply false
    @Suppress("DSL_SCOPE_VIOLATION")
    alias(libs.plugins.android.library) apply false
    @Suppress("DSL_SCOPE_VIOLATION")
    alias(libs.plugins.kotlin.android) apply false
    @Suppress("DSL_SCOPE_VIOLATION")
    alias(libs.plugins.hilt) apply false
    //id("com.android.application") version "7.3.1" apply false
    //id("com.android.library") version "7.3.1" apply false
    //id("org.jetbrains.kotlin.android") version "1.7.10" apply false
    //id("com.google.dagger.hilt.android") version "2.44" apply false
    //id("org.jetbrains.kotlin.jvm") version "1.7.20" apply false
}