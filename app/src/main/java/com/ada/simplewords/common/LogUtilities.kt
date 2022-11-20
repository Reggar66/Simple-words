package com.ada.simplewords.common

import android.util.Log

fun debugLog(TAG: String = "DebugLog", msg: () -> Any?) = Log.d(TAG, msg().toString())