package com.example.simplewords.feature.details

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.simplewords.common.OnClick
import com.example.simplewords.common.debugLog
import com.example.simplewords.data.WordTranslation
import com.example.simplewords.feature.list.QuizData
import com.example.simplewords.ui.components.PreviewContainer
import com.example.simplewords.ui.components.WordItem

@Composable
fun QuizDetailsScreen(quizId: Int?) {

    // TODO change to fetching data from db
    // Simulate fetching from db
    val quizzes = QuizData.mock
    val quiz = quizzes.find { it.quizItem.id == quizId }

    debugLog { "Fetched quiz: $quiz" }

    if (quiz != null) {
        QuizDetails(quiz) { }
    } else {
        Text(text = "No words :(")
    }
}

@Composable
private fun QuizDetails(quizData: QuizData, onLearnClick: OnClick) {
    Box(modifier = Modifier.fillMaxWidth()) {
        LazyColumn(
            contentPadding = PaddingValues(start = 8.dp, top = 8.dp, end = 8.dp, bottom = 54.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(items = quizData.words) { item ->
                WordItem(wordTranslation = item)
            }
        }
        Button(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(4.dp),
            onClick = onLearnClick,
            shape = CircleShape
        ) {
            Text(text = "Learn") // TODO strings
        }
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun QuizDetailsPreview() {
    PreviewContainer {
        QuizDetails(QuizData.mock.first()) { }
    }
}