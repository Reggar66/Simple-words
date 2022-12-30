package com.ada.quizdetails

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.ada.common.OnClick
import com.ada.data.model.QuizMode
import com.ada.ui.PreviewContainer
import com.ada.ui.PreviewDuo
import com.ada.ui.components.BottomSheetContainer
import com.ada.ui.components.WordItem

@Composable
fun QuizDetailsScreen(quizId: String?, onLearnClick: OnClick, onCloseClick: OnClick) {
    val viewModel = hiltViewModel<QuizDetailsViewModel>()

    var showConfirmation by remember {
        mutableStateOf(false)
    }

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
        onRedoClick = { showConfirmation = true },
        onCloseClick = onCloseClick,
        onModeClick = { viewModel.changeMode() }
    )

    if (showConfirmation)
        ConfirmationDialog(
            onDismissRequest = { showConfirmation = false },
            onYesClick = {
                showConfirmation = false
                viewModel.restartQuiz().invokeOnCompletion {
                    onLearnClick()
                }
            },
            onNoClick = { showConfirmation = false })
}

@Composable
private fun ConfirmationDialog(
    onDismissRequest: () -> Unit,
    onYesClick: OnClick,
    onNoClick: OnClick
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Card {
            Column(
                modifier = Modifier.padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(text = "Are you sure you want to redo exercise?")
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(modifier = Modifier
                        .clickable { onYesClick() }
                        .padding(4.dp), text = "Yes")
                    Spacer(modifier = Modifier.width(32.dp))
                    Text(
                        modifier = Modifier
                            .clickable { onNoClick() }
                            .padding(4.dp),
                        text = "No"
                    )
                }
            }
        }
    }
}

@Composable
private fun QuizDetails(
    quizDetailsState: QuizDetailsState,
    onLearnClick: OnClick,
    onRedoClick: OnClick,
    onCloseClick: OnClick,
    onModeClick: OnClick
) {
    val isComplete = quizDetailsState.quiz?.isComplete

    BottomSheetContainer(title = quizDetailsState.quiz?.name, onCloseClick = onCloseClick) {
        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier.weight(1f),
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

            BottomPanel(
                currentModeName = quizDetailsState.quiz?.mode,
                onModeClick = onModeClick,
                isComplete = isComplete,
                onRedoClick = onRedoClick,
                onLearnClick = onLearnClick
            )
        }
    }
}

@Composable
private fun BottomPanel(
    currentModeName: QuizMode?,
    onModeClick: OnClick,
    isComplete: Boolean?,
    onRedoClick: OnClick,
    onLearnClick: OnClick
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        val modeName = when (currentModeName) {
            QuizMode.Classic -> "Classic" // TODO: strings.xml
            QuizMode.Modern -> "Modern" // TODO: strings.xml
            else -> ""
        }

        currentModeName?.let {
            Button(onClick = onModeClick, shape = CircleShape) {
                Text(text = "Mode: $modeName")
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        Button(
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

@PreviewDuo
@Composable
private fun QuizDetailsPreview() {
    PreviewContainer {
        QuizDetails(
            QuizDetailsState.mock(), {}, {}, {}) {}
    }
}