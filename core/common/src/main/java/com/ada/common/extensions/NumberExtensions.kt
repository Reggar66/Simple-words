package com.ada.common.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit

@Composable
fun Dp.toPx(): Float {
    with(LocalDensity.current) {
        return this@toPx.toPx()
    }
}

@Composable
fun Int.toDp(): Dp {
    with(LocalDensity.current) {
        return this@toDp.toDp()
    }
}

@Composable
fun Int.toSp(): TextUnit {
    with(LocalDensity.current) {
        return this@toSp.toSp()
    }
}

