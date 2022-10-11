package com.example.simplewords.feature.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.simplewords.common.OnClick
import com.example.simplewords.common.OnClickWithType
import com.example.simplewords.data.QuizItem
import com.example.simplewords.data.WordTranslation
import com.example.simplewords.ui.navigation.SimpleNavigation

data class QuizData(val quizItem: QuizItem, val words: List<WordTranslation>) {
    companion object {
        val mock by lazy {
            listOf(QuizData(quizItem = QuizItem.mockAnimals, words = WordTranslation.mockAnimals))
        }
    }
}

@Composable
fun QuizListScreen(openQuiz: SimpleNavigation) {
    // TODO pass data with viewModel
    QuizList(quizData = QuizData.mock, onItemCLick = {/*TODO navigate to quiz*/ })
}

@Composable
private fun QuizList(quizData: List<QuizData>, onItemCLick: OnClickWithType<QuizData>) {
    LazyColumn(contentPadding = PaddingValues(4.dp)) {
        items(items = quizData) { itemQuizData ->
            QuizItem(quizData = itemQuizData, onClick = { onItemCLick(itemQuizData) })
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun QuizItem(quizData: QuizData, onClick: OnClick) {
    Card(modifier = Modifier.fillMaxWidth(), onClick = onClick) {
        Column(modifier = Modifier.padding(4.dp)) {
            Text(text = quizData.quizItem.name)
            Text(text = "Words: ${quizData.words.size}")
            Text(text = "Progress: 0/${quizData.words.size}")// TODO progress counter
        }
    }
}

@Preview
@Composable
private fun QuizListPreview() {
    QuizList(quizData = QuizData.mock, onItemCLick = {})
}