package com.ada.simplewords.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ada.simplewords.common.OnClick
import com.ada.data.Quiz
import com.ada.data.mapper.toQuizOrNull
import com.example.domain.models.QuizModel
import com.ada.simplewords.ui.components.utility.PreviewContainer
import com.ada.simplewords.ui.theme.dimensions

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun QuizItem(modifier: Modifier = Modifier, quiz: com.ada.data.Quiz, onClick: OnClick) {
    Card(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        elevation = if (quiz.isComplete) MaterialTheme.dimensions.quizItemCompleteElevation else MaterialTheme.dimensions.quizItemElevation
    ) {
        Column(modifier = Modifier.padding(4.dp)) {
            quiz.name?.let { Text(text = it) }
            Text(text = "Words: ${quiz.wordsNumber}")
            Text(text = "Progress: ${quiz.completedWords}/${quiz.wordsNumber}")
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Preview
@Composable
private fun QuizItemPreview() {
    PreviewContainer(modifier = Modifier.padding(8.dp)) {
        QuizItem(quiz = com.example.domain.models.QuizModel.mockSeasons.toQuizOrNull() ?: com.ada.data.Quiz.empty()) {

        }
    }
}