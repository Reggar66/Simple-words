@file:OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)

package com.ada.quizlist

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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ada.common.OnClick
import com.ada.common.OnClickTakes
import com.ada.common.SimpleNavigation
import com.ada.common.SimpleNavigationTakes
import com.ada.data.model.UserModel
import com.ada.domain.mapper.toUserOrNull
import com.ada.domain.model.Quiz
import com.ada.domain.model.User
import com.ada.quizdetails.QuizDetailsScreen
import com.ada.ui.PreviewContainer
import com.ada.ui.PreviewDuo
import com.ada.ui.components.QuizItem
import com.ada.ui.components.AccountBar
import com.ada.ui.components.SimpleModalBottomSheetLayout
import com.ada.ui.components.SwipeMenu
import com.ada.ui.theme.bottomSheetShape
import kotlinx.coroutines.launch

@Composable
fun QuizListScreen(
    openExercise: SimpleNavigationTakes<Quiz>,
    openCreate: SimpleNavigation,
    openAccount: SimpleNavigation,
    openQuizEdit: SimpleNavigationTakes<Quiz>
) {
    val viewModel = hiltViewModel<QuizListViewModel>()
    val state = viewModel.quizListState

    val user by viewModel.user.collectAsState(initial = null)
    val modalSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    var quizToRemoveName by remember {
        mutableStateOf("")
    }

    SimpleModalBottomSheetLayout(
        sheetContent = {
            DeleteBottomSheet(
                message = "Remove $quizToRemoveName?", // TODO: string
                onYesClick = {
                    viewModel.removeQuiz()
                    scope.launch {
                        modalSheetState.hide()
                    }
                },
                onNoClick = {
                    scope.launch {
                        modalSheetState.hide()
                    }
                }
            )
        },
        sheetState = modalSheetState
    ) {
        QuizListImpl(
            quizListState = state,
            onLearnClick = { quiz -> openExercise(quiz) },
            onItemClick = { viewModel.selectQuiz(it) },
            onCreateClick = { openCreate() },
            user = user,
            onAccountClick = { openAccount() },
            onRemoveClick = {
                quizToRemoveName = it.name
                viewModel.setQuizForRemove(quiz = it)
                scope.launch {
                    modalSheetState.show()
                }
            },
            onEditClick = openQuizEdit
        )
    }
}

@Composable
private fun QuizListImpl(
    quizListState: QuizListScreenState,
    onLearnClick: OnClickTakes<Quiz>,
    onItemClick: OnClickTakes<Quiz>,
    onCreateClick: OnClick,
    user: User?,
    onAccountClick: OnClick,
    onRemoveClick: OnClickTakes<Quiz>,
    onEditClick: OnClickTakes<Quiz>
) {
    val scaffoldState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()

    BottomSheetScaffold(
        sheetContent = {
            QuizDetailsScreen(
                quizId = quizListState.currentlySelectedQuiz?.id,
                onLearnClick = { quizListState.currentlySelectedQuiz?.let { onLearnClick(it) } },
                onCloseClick = {
                    scope.launch {
                        scaffoldState.bottomSheetState.collapse()
                    }
                }
            )
        },
        scaffoldState = scaffoldState,
        sheetShape = MaterialTheme.shapes.bottomSheetShape,
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
                onCreateClick = onCreateClick,
                user = user,
                onAccountClick = onAccountClick,
                onRemoveClick = onRemoveClick,
                onEditClick = onEditClick
            )
        }
    )
}

@Composable
private fun Quizzes(
    quiz: List<Quiz>,
    onItemCLick: OnClickTakes<Quiz>,
    onCreateClick: OnClick,
    user: User?,
    onAccountClick: OnClick,
    onRemoveClick: OnClickTakes<Quiz>,
    onEditClick: OnClickTakes<Quiz>
) {
    Column {
        AccountBar(user = user, onPictureClick = { onAccountClick() })

        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(items = quiz, key = { it.id }) { quizItem ->
                    SwipeMenu(
                        buttonSize = 80.dp,
                        onRemoveClick = { onRemoveClick(quizItem) },
                        onEditClick = { onEditClick(quizItem) }) {
                        QuizItem(
                            modifier = Modifier
                                .animateItemPlacement(),
                            quiz = quizItem,
                            onClick = { onItemCLick(quizItem) })
                    }
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
}

@PreviewDuo
@Composable
private fun QuizListPreview() {
    PreviewContainer {
        Quizzes(
            quiz = Quiz.mockQuizzes(),
            onItemCLick = {},
            onCreateClick = {},
            onAccountClick = {},
            user = UserModel.mock().toUserOrNull(),
            onRemoveClick = {},
            onEditClick = {}
        )
    }
}