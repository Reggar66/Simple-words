package com.example.simplewords.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.simplewords.ui.theme.SimpleWordsTheme

@Composable
fun PreviewContainer(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    SimpleWordsTheme {
        Surface(color = MaterialTheme.colors.background) {
            Column(modifier = modifier) {
                content()
            }
        }
    }
}