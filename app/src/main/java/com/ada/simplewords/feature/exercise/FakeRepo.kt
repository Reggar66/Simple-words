package com.ada.simplewords.feature.exercise

import com.ada.simplewords.common.debugLog
import javax.inject.Inject

/* TODO remove */
class FakeRepo @Inject constructor() {
    fun repoCall() = debugLog { "Fake Repo: repoCall()" }
}