package com.ada.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

interface Dimensions {
    val quizItemCompleteElevation: Dp get() = 0.dp
    val quizItemElevation: Dp get() = 1.dp
}

val MaterialTheme.dimensions get() = object : Dimensions {}