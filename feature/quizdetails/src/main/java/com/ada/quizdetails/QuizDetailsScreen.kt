package com.ada.quizdetails

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
import androidx.hilt.navigation.compose.hiltViewModel
import com.ada.common.OnClick
import com.ada.ui.PreviewContainer
import com.ada.ui.PreviewDuo
import com.ada.ui.components.BottomSheetContainer
import com.ada.ui.components.WordItem

@Composable
fun QuizDetailsScreen(quizId: String?, onLearnClick: OnClick, onCloseClick: OnClick) {
    val viewModel = hiltViewModel<QuizDetailsViewModel>()

    LaunchedEffect(
        key1 = quizId,
        block = {
            quizId?.let {
                viewModel.observeQuiz(it)
                viewModel.observeWords(it)
            }
        }
    )

    val state = viewModel.quizDetailsState.collectAsState()

    QuizDetails(
        state.value,
        onLearnClick = onLearnClick,
        onRedoClick = {
            // TODO: Add confirmation dialog.
            viewModel.restartQuiz().invokeOnCompletion {
                onLearnClick()
            }
        },
        onCloseClick = onCloseClick
    )
}

@Composable
private fun QuizDetails(
    quizDetailsState: QuizDetailsState,
    onLearnClick: OnClick,
    onRedoClick: OnClick,
    onCloseClick: OnClick
) {
    val isComplete = quizDetailsState.quiz?.isComplete

    BottomSheetContainer(title = quizDetailsState.quiz?.name, onCloseClick = onCloseClick) {
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                contentPadding = PaddingValues(
                    start = 8.dp,
                    top = 8.dp,
                    end = 8.dp,
                    bottom = 54.dp
                ),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(items = quizDetailsState.words) { item ->
                    WordItem(
                        leftText = item.second.word,
                        rightText = item.second.translation,
                        isLearned = item.second.isLearned
                    )
                }
            }
            Button(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(4.dp),
                onClick = {
                    when (isComplete) {
                        true -> {
                            onRedoClick()
                        }
                        else -> {
                            onLearnClick()
                        }
                    }
                },
                shape = CircleShape
            ) {
                Text(
                    text = when (isComplete) {
                        true -> "Redo" // TODO strings
                        else -> "Learn"
                    }
                )
            }
        }
    }
}

@PreviewDuo
@Composable
private fun QuizDetailsPreview() {
    PreviewContainer {
        QuizDetails(
            QuizDetailsState.mock(), {}, {}) {}
    }
}