package com.ada.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ada.ui.theme.SimpleWordsTheme

@Composable
fun PreviewContainer(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    SimpleWordsTheme {
        Surface(color = MaterialTheme.colors.surface) {
            Column(modifier = modifier) {
                content()
            }
        }
    }
}