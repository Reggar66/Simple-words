package com.example.simplewords.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.simplewords.common.OnClick
import com.example.simplewords.feature.list.QuizData

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun QuizItem(modifier: Modifier = Modifier, quizData: QuizData, onClick: OnClick) {
    Card(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick
    ) {
        Column(modifier = Modifier.padding(4.dp)) {
            Text(text = quizData.quizItem.name)
            Text(text = "Words: ${quizData.words.size}")
            Text(text = "Progress: ${quizData.learnedWords.size}/${quizData.words.size}")
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Preview
@Composable
private fun QuizItemPreview() {
    PreviewContainer {
        QuizItem(quizData = QuizData.mock.first()) {

        }
    }
}