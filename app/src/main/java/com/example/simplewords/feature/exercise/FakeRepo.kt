package com.example.simplewords.feature.exercise

import com.example.simplewords.common.debugLog
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

/* TODO remove */
class FakeRepo @Inject constructor() {
    fun repoCall() = debugLog { "Fake Repo: repoCall()" }
}