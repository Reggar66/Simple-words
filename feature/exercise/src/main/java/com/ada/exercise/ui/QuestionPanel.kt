package com.ada.exercise.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ada.ui.PreviewContainer
import com.ada.ui.PreviewDuo
import com.ada.ui.theme.exerciseWord

@Composable
internal fun QuestionPanel(modifier: Modifier = Modifier, question: String, repeats: Int) {
    Box(modifier = Modifier
        .defaultMinSize(minWidth = 200.dp, minHeight = 200.dp)
        .then(modifier)) {
        Text(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 8.dp),
            text = question,
            style = MaterialTheme.typography.exerciseWord
        )
        Text(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(8.dp),
            text = "Repeats: $repeats"
        )
    }
}

@PreviewDuo
@Composable
private fun QuestionPanel() {
    PreviewContainer {
        QuestionPanel(question = "Dog", repeats = 3)
    }
}