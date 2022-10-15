package com.example.simplewords.feature.list

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.simplewords.common.OnClick
import com.example.simplewords.common.OnClickType
import com.example.simplewords.data.QuizItem
import com.example.simplewords.data.WordTranslation
import com.example.simplewords.ui.components.PreviewContainer
import com.example.simplewords.ui.navigation.SimpleNavigationParam

data class QuizData(
    val quizItem: QuizItem,
    val words: List<WordTranslation>,
    val learnedWords: List<WordTranslation>
) {
    companion object {
        val mock by lazy {
            listOf(
                QuizData(
                    quizItem = QuizItem.mockFood,
                    words = WordTranslation.mockFood,
                    learnedWords = WordTranslation.mockFood.filter { it.isLearned }),
                QuizData(
                    quizItem = QuizItem.mockAnimals,
                    words = WordTranslation.mockAnimals,
                    learnedWords = WordTranslation.mockAnimals.filter { it.isLearned }),
                QuizData(
                    quizItem = QuizItem.mockAnimalsCompleted,
                    words = WordTranslation.mockAnimalsCompleted,
                    learnedWords = WordTranslation.mockAnimalsCompleted.filter { it.isLearned }),
                QuizData(
                    quizItem = QuizItem.mockSeasons,
                    words = WordTranslation.mockSeasons,
                    learnedWords = WordTranslation.mockSeasons.filter { it.isLearned }),
            )
        }
    }
}

@Composable
fun QuizListScreen(openQuiz: SimpleNavigationParam<QuizData>) {
    val viewModel = viewModel<QuizListViewModel>()
    val state = viewModel.quizListState

    QuizList(
        quizData = state.quizzes,
        onItemCLick = { openQuiz(it) },
        onSortClick = { viewModel.sortByName() },
        onShuffleClick = { viewModel.shuffle() }
    )
}

@Composable
private fun QuizList(
    quizData: List<QuizData>,
    onItemCLick: OnClickType<QuizData>,
    onSortClick: OnClick,
    onShuffleClick: OnClick
) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            contentPadding = PaddingValues(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(items = quizData, key = { it.quizItem.id }) { itemQuizData ->
                QuizItem(
                    quizData = itemQuizData,
                    onClick = { onItemCLick(itemQuizData) })
            }
        }

        // TODO remove buttons
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(onClick = onSortClick) {
                Text(text = "Sort")
            }
            Button(onClick = onShuffleClick) {
                Text(text = "Shuffle")
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
private fun LazyItemScope.QuizItem(quizData: QuizData, onClick: OnClick) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateItemPlacement(),
        onClick = onClick
    ) {
        Column(modifier = Modifier.padding(4.dp)) {
            Text(text = quizData.quizItem.name)
            Text(text = "Words: ${quizData.words.size}")
            Text(text = "Progress: ${quizData.learnedWords.size}/${quizData.words.size}")
        }
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun QuizListPreview() {
    PreviewContainer {
        QuizList(quizData = QuizData.mock, onItemCLick = {}, {}) {}
    }
}