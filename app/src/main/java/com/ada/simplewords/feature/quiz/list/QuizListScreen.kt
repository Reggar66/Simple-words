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
import com.ada.simplewords.data.QuizData
import com.ada.simplewords.domain.models.QuizItemModel
import com.ada.simplewords.domain.models.UserModel
import com.ada.simplewords.domain.models.WordTranslationModel
import com.ada.simplewords.feature.quiz.details.QuizDetailsScreen
import com.ada.simplewords.ui.components.QuizItem
import com.ada.simplewords.ui.components.utility.PreviewContainer
import com.ada.simplewords.ui.navigation.SimpleNavigationTakes
import kotlinx.coroutines.launch

@Composable
fun QuizListScreen(openExercise: SimpleNavigationTakes<QuizData>) {
    val viewModel = hiltViewModel<QuizListViewModel>()
    val state = viewModel.quizListState

    var showDebug by remember {
        mutableStateOf(false)
    }

    Box {
        QuizListImpl(
            quizListState = state,
            onLearnClick = { quizData -> openExercise(quizData) },
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
                Button(onClick = { viewModel.firebaseRepository.saveQuiz(QuizItemModel.mockAnimals) }) {
                    Text(text = "Generate Mock Quiz")
                }

                Button(onClick = { viewModel.firebaseRepository.saveQuiz(QuizItemModel.mockFood) }) {
                    Text(text = "Generate Mock Quiz 2")
                }

                Button(onClick = {
                    viewModel.firebaseRepository.saveQuizWords(
                        "-NHLALPJBHBDbKfnnQR1",
                        WordTranslationModel.mockAnimals
                    )
                }) {
                    Text(text = "Generate Mock Words")
                }
            }
    }
}

@Composable
private fun QuizListImpl(
    quizListState: QuizListScreenState,
    onLearnClick: OnClickTakes<QuizData>,
    onItemCLick: OnClickTakes<QuizData>
) {
    val scaffoldState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()

    BottomSheetScaffold(
        sheetContent = {
            BottomSheetContent(
                quizData = quizListState.currentlySelectedQuiz,
                onLearnClick = { quizListState.currentlySelectedQuiz?.let { onLearnClick(it) } }
            )
        },
        scaffoldState = scaffoldState,
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        sheetPeekHeight = 0.dp,
        content = {
            Quizzes(
                quizData = quizListState.quizzes,
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
private fun BottomSheetContent(quizData: QuizData?, onLearnClick: OnClick) {
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
        QuizDetailsScreen(quizId = quizData?.quizItem?.id, onLearnClick = onLearnClick)
    }
}

@Composable
private fun Quizzes(
    quizData: List<QuizData>,
    onItemCLick: OnClickTakes<QuizData>
) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            contentPadding = PaddingValues(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(items = quizData, key = { it.quizItem.id }) { itemQuizData ->
                QuizItem(
                    modifier = Modifier
                        .animateItemPlacement(),
                    quizItem = itemQuizData.quizItem,
                    onClick = { onItemCLick(itemQuizData) })
            }
        }
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun QuizListPreview() {
    PreviewContainer {
        Quizzes(quizData = QuizData.mock, onItemCLick = {})
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun BottomSheetPreview() {
    PreviewContainer {
        BottomSheetContent(quizData = QuizData.mock.first(), onLearnClick = {})
    }
}