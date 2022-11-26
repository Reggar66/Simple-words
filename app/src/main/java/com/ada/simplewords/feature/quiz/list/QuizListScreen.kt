@file:OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)

package com.ada.simplewords.feature.quiz.list

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ada.simplewords.common.OnClick
import com.ada.simplewords.common.OnClickTakes
import com.ada.simplewords.data.Quiz
import com.ada.simplewords.data.toQuizItemOrEmpty
import com.ada.simplewords.domain.models.QuizModel
import com.ada.simplewords.domain.models.UserModel
import com.ada.simplewords.feature.quiz.details.QuizDetailsScreen
import com.ada.simplewords.ui.components.QuizItem
import com.ada.simplewords.ui.components.utility.PreviewContainer
import com.ada.simplewords.ui.navigation.SimpleNavigationTakes
import kotlinx.coroutines.launch

@Composable
fun QuizListScreen(openExercise: SimpleNavigationTakes<Quiz>) {
    val viewModel = hiltViewModel<QuizListViewModel>()
    val state = viewModel.quizListState

    var showDebug by remember {
        mutableStateOf(false)
    }

    Box {
        QuizListImpl(
            quizListState = state,
            onLearnClick = { quiz -> openExercise(quiz) },
            onItemCLick = { viewModel.selectQuiz(it) })

        Button(
            modifier = Modifier.align(Alignment.BottomStart),
            onClick = { showDebug = !showDebug }) {
            Text(text = "Debug")
        }

        // TODO remove buttons
        if (showDebug)
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(onClick = { viewModel.firebaseRepository.saveUser(UserModel.mock()) }) {
                    Text(text = "Generate Mock User")
                }

                Button(onClick = { viewModel.firebaseRepository.Debug().mockAnimals() }) {
                    Text(text = "Generate Animals")
                }

                Button(onClick = { viewModel.firebaseRepository.Debug().mockAnimalsCompleted() }) {
                    Text(text = "Generate Animals Completed")
                }

                Button(onClick = { viewModel.firebaseRepository.Debug().mockFood() }) {
                    Text(text = "Generate Food")
                }

                Button(onClick = { viewModel.firebaseRepository.Debug().mockSeasons() }) {
                    Text(text = "Generate Seasons")
                }
            }
    }
}

@Composable
private fun QuizListImpl(
    quizListState: QuizListScreenState,
    onLearnClick: OnClickTakes<Quiz>,
    onItemCLick: OnClickTakes<Quiz>
) {
    val scaffoldState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()

    BottomSheetScaffold(
        sheetContent = {
            BottomSheetContent(
                quiz = quizListState.currentlySelectedQuiz,
                onLearnClick = { quizListState.currentlySelectedQuiz?.let { onLearnClick(it) } }
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
                        if (scaffoldState.bottomSheetState.isExpanded)
                            scaffoldState.bottomSheetState.collapse()
                        onItemCLick(it)
                        scaffoldState.bottomSheetState.expand()
                    }
                }
            )
        })
}

@Composable
private fun BottomSheetContent(quiz: Quiz?, onLearnClick: OnClick) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.height(8.dp))
        // TODO change card bar to something better.
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
                .padding(horizontal = 100.dp),
            shape = CircleShape
        ) {}
        QuizDetailsScreen(quizId = quiz?.id, onLearnClick = onLearnClick)
    }
}

@Composable
private fun Quizzes(
    quiz: List<Quiz>,
    onItemCLick: OnClickTakes<Quiz>
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
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun QuizListPreview() {
    PreviewContainer {
        Quizzes(quiz = Quiz.mockQuizzes(), onItemCLick = {})
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun BottomSheetPreview() {
    PreviewContainer {
        BottomSheetContent(quiz = QuizModel.mockAnimals.toQuizItemOrEmpty(), onLearnClick = {})
    }
}