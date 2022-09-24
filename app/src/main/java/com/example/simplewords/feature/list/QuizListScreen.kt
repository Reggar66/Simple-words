package com.example.simplewords.feature.list

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.simplewords.ui.navigation.SimpleNavigation

@Composable
fun QuizListScreen(openQuiz: SimpleNavigation) {
    QuizList()
}

@Composable
private fun QuizList() {
    LazyColumn() {
        items(listOf(1, 2, 3, 4)) { item ->
            Text(text = "item: $item")
        }
    }
}

@Preview
@Composable
private fun QuizListPreview() {
    QuizList()
}