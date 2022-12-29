@file:OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)

package com.ada.quizlist

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ada.common.OnClick
import com.ada.common.OnClickTakes
import com.ada.common.SimpleNavigation
import com.ada.common.SimpleNavigationTakes
import com.ada.data.model.QuizModel
import com.ada.domain.mapper.toQuizOrEmpty
import com.ada.domain.model.Quiz
import com.ada.ui.PreviewContainer
import com.ada.ui.components.QuizItem
import com.ada.quizdetails.QuizDetailsScreen
import kotlinx.coroutines.launch

@Composable
fun QuizListScreen(openExercise: SimpleNavigationTakes<Quiz>, openCreate: SimpleNavigation) {
    val viewModel = hiltViewModel<QuizListViewModel>()
    val state = viewModel.quizListState

    QuizListImpl(
        quizListState = state,
        onLearnClick = { quiz -> openExercise(quiz) },
        onItemClick = { viewModel.selectQuiz(it) },
        onCreateClick = { openCreate() }
    )
}

@Composable
private fun QuizListImpl(
    quizListState: QuizListScreenState,
    onLearnClick: OnClickTakes<Quiz>,
    onItemClick: OnClickTakes<Quiz>,
    onCreateClick: OnClick
) {
    val scaffoldState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()

    BottomSheetScaffold(
        sheetContent = {
            BottomSheetContainer(
                quizName = quizListState.currentlySelectedQuiz?.name,
                onCloseClick = {
                    scope.launch {
                        scaffoldState.bottomSheetState.collapse()
                    }
                },
                content = {
                    QuizDetailsScreen(
                        quizId = quizListState.currentlySelectedQuiz?.id,
                        onLearnClick = { quizListState.currentlySelectedQuiz?.let { onLearnClick(it) } }
                    )
                }
            )
        },
        scaffoldState = scaffoldState,
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        sheetPeekHeight = 0.dp,
        content = {
            Quizzes(
                quiz = quizListState.quizzes,
                onItemCLick = {
                    scope.launch {
                        onItemClick(it)
                        scaffoldState.bottomSheetState.expand()
                    }
                },
                onCreateClick = onCreateClick
            )
        }
    )
}

@Composable
private fun BottomSheetContainer(
    quizName: String?,
    onCloseClick: OnClick,
    content: @Composable () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center) {
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            Icon(
                modifier = Modifier
                    .clickable { onCloseClick() }
                    .size(40.dp)
                    .clip(CircleShape),
                imageVector = Icons.Rounded.KeyboardArrowDown,
                contentDescription = null
            )

            Text(
                text = quizName ?: "",
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                fontSize = 24.sp
            )

            Spacer(modifier = Modifier.width(40.dp))
        }

        content()
    }
}

@Composable
private fun Quizzes(
    quiz: List<Quiz>,
    onItemCLick: OnClickTakes<Quiz>,
    onCreateClick: OnClick
) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            contentPadding = PaddingValues(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(items = quiz, key = { it.id }) { quizItem ->
                QuizItem(
                    modifier = Modifier
                        .animateItemPlacement(),
                    quiz = quizItem,
                    onClick = { onItemCLick(quizItem) })
            }
        }

        Button(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            onClick = onCreateClick,
            shape = CircleShape
        ) {
            Text(text = "Create") // TODO: Strings
        }
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun QuizListPreview() {
    PreviewContainer {
        Quizzes(quiz = Quiz.mockQuizzes(), onItemCLick = {}, onCreateClick = {})
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun BottomSheetPreview() {
    PreviewContainer {
        BottomSheetContainer(
            quizName = QuizModel.mockAnimals.toQuizOrEmpty().name,
            onCloseClick = {},
            content = {
                Column(
                    modifier = Modifier
                        .background(Color.Gray)
                        .fillMaxWidth()
                        .height(200.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Content")
                }
            }
        )
    }
}