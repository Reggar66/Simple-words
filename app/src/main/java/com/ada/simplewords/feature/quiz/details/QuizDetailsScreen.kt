package com.ada.simplewords.feature.quiz.details

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ada.simplewords.common.OnClick
import com.ada.simplewords.common.debugLog
import com.ada.simplewords.data.QuizData
import com.ada.simplewords.ui.components.utility.PreviewContainer
import com.ada.simplewords.ui.components.WordItem

@Composable
fun QuizDetailsScreen(quizId: Int?, onLearnClick: OnClick) {

    // TODO change to fetching data from db
    // Simulate fetching from db
    val quizzes by remember {
        mutableStateOf(QuizData.mock)
    }

    var quiz: QuizData? by remember {
        mutableStateOf(quizzes.find { it.quizItem.id == quizId })
    }

    LaunchedEffect(
        key1 = quizId,
        block = {
            quiz = quizzes.find { it.quizItem.id == quizId }
        }
    )
    debugLog { "Fetched quiz: $quiz" }

    quiz?.let { QuizDetails(it, onLearnClick = onLearnClick) }

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