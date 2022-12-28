package com.ada.simplewords.helper

import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.api.plugins.ExtensionContainer
import org.gradle.kotlin.dsl.getByType

fun ExtensionContainer.getLibs(): VersionCatalog =
    getByType<VersionCatalogsExtension>().named("libs")