package com.ada.simplewords.helper

import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.DependencyHandlerScope

fun <T : Any?> DependencyHandlerScope.implementation(libProvider: Provider<T>) =
    addProvider("implementation", libProvider)

fun <T : Any?> DependencyHandlerScope.kapt(libProvider: Provider<T>) =
    addProvider("kapt", libProvider)

fun <T : Any?> DependencyHandlerScope.androidTestImplementation(libProvider: Provider<T>) =
    addProvider("androidTestImplementation", libProvider)

fun <T : Any?> DependencyHandlerScope.debugImplementation(libProvider: Provider<T>) =
    addProvider("debugImplementation", libProvider)