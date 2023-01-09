package com.ada.quizedit

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ada.common.OnClick
import com.ada.common.OnClickTakes
import com.ada.common.SimpleNavigation
import com.ada.domain.model.Quiz
import com.ada.domain.model.WordTranslation
import com.ada.quizlist.DeleteBottomSheet
import com.ada.ui.PreviewContainer
import com.ada.ui.PreviewDuo
import com.ada.ui.components.*
import com.ada.ui.theme.bottomSheetShape
import com.ada.ui.theme.quizTitle
import com.ada.ui.theme.topBarTitle
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun QuizEditScreen(quizId: String?, closeScreen: SimpleNavigation) {
    val viewModel = hiltViewModel<QuizEditViewModel>()
    val quizEditState by viewModel.quizEditState.collectAsState()
    val scope = rememberCoroutineScope()


    LaunchedEffect(key1 = quizId, block = {
        quizId?.let {
            viewModel.observeQuiz(quizId = it)
            viewModel.observeTranslations(quizId = it)
        }
    })

    var wordToDeleteName by remember {
        mutableStateOf("")
    }
    val modalSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

    val scaffoldState = rememberBottomSheetScaffoldState()

    SimpleModalBottomSheetLayout(
        sheetContent = {
            DeleteBottomSheet(
                message = "Delete $wordToDeleteName?", // TODO: string
                onYesClick = {
                    scope.launch {
                        viewModel.deleteMarkedWord()
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
        BottomSheetScaffold(
            sheetContent = {
                EditBox(
                    quizEditState = quizEditState,
                    onCloseClick = {
                        scope.launch {
                            scaffoldState.bottomSheetState.collapse()
                        }
                    },
                    onConfirmWordClick = { newWord ->
                        viewModel.updateWordTranslation(newWord)
                    },
                    onConfirmQuizClick = { newQuiz ->
                        viewModel.updateQuiz(newQuiz)
                    }
                )
            },
            sheetShape = MaterialTheme.shapes.bottomSheetShape,
            scaffoldState = scaffoldState,
            sheetPeekHeight = 0.dp
        ) {

            QuizEditImpl(
                quizEditState = quizEditState,
                onBackArrowClick = closeScreen,
                onRemoveClick = {
                    wordToDeleteName = "${it.word} | ${it.translation}"
                    viewModel.markWordForDeletion(it)
                    scope.launch {
                        modalSheetState.show()
                    }
                },
                onEditWordClick = {
                    scope.launch {
                        if (scaffoldState.bottomSheetState.isExpanded)
                            scaffoldState.bottomSheetState.collapse()
                        viewModel.changeEditMode(editMode = EditMode.Word)
                        viewModel.setWordToEdit(it)
                        scaffoldState.bottomSheetState.expand()
                    }
                },
                onEditQuizNameClick = {
                    scope.launch {
                        if (scaffoldState.bottomSheetState.isExpanded)
                            scaffoldState.bottomSheetState.collapse()
                        viewModel.changeEditMode(editMode = EditMode.Quiz)
                        scaffoldState.bottomSheetState.expand()
                    }
                },
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun QuizEditImpl(
    quizEditState: QuizEditState,
    onBackArrowClick: OnClick,
    onRemoveClick: OnClickTakes<WordTranslation>,
    onEditWordClick: OnClickTakes<WordTranslation>,
    onEditQuizNameClick: OnClick,
) {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        TopBar(onBackArrowClick = onBackArrowClick) {
            Text(
                text = "Quiz Edit", // TODO: strings
                style = MaterialTheme.typography.topBarTitle
            )
        }

        Text(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .clickable { onEditQuizNameClick() },
            text = quizEditState.quiz.name,
            style = MaterialTheme.typography.quizTitle
        )

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(items = quizEditState.words, key = { it.id }) { wordItem ->
                SwipeMenu(
                    onRemoveClick = { onRemoveClick(wordItem) },
                    onEditClick = { onEditWordClick(wordItem) }
                ) {
                    WordItem(
                        leftText = wordItem.word,
                        rightText = wordItem.translation
                    )
                }
            }
        }
    }
}

@Composable
private fun EditBox(
    modifier: Modifier = Modifier,
    quizEditState: QuizEditState,
    onCloseClick: OnClick,
    onConfirmWordClick: OnClickTakes<WordTranslation>,
    onConfirmQuizClick: OnClickTakes<Quiz>
) {
    Column(modifier = modifier) {
        BottomSheetContainer(title = "", onCloseClick = onCloseClick) {
            when (quizEditState.editMode) {
                EditMode.Quiz -> {
                    QuizEdit(
                        quiz = quizEditState.quiz,
                        onConfirmQuizClick = onConfirmQuizClick
                    )
                }
                EditMode.Word -> {
                    quizEditState.wordToEdit?.let {
                        WordEdit(
                            wordTranslation = it,
                            onConfirmWordClick = onConfirmWordClick
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun WordEdit(
    wordTranslation: WordTranslation,
    onConfirmWordClick: OnClickTakes<WordTranslation>,
) {
    var word by remember { mutableStateOf("") }
    var translation by remember { mutableStateOf("") }
    LaunchedEffect(key1 = wordTranslation.id, block = {
        word = wordTranslation.word
        translation = wordTranslation.translation
    })
    val keyboardController = LocalSoftwareKeyboardController.current


    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = word,
            onValueChange = { word = it },
            label = {
                Text(text = "Word")
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = translation,
            onValueChange = { translation = it },
            label = {
                Text(text = "Translation")
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = { keyboardController?.hide() },
                onGo = {},
                onNext = {},
                onPrevious = {},
                onSearch = {},
                onSend = {})
        )

        SimpleButton(onClick = {
            onConfirmWordClick(
                wordTranslation.copy(
                    word = word,
                    translation = translation
                )
            )
        }) {
            Text(text = "Confirm")
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun QuizEdit(
    quiz: Quiz,
    onConfirmQuizClick: OnClickTakes<Quiz>
) {

    var quizName by remember { mutableStateOf("") }

    LaunchedEffect(key1 = quiz.name, block = {
        quizName = quiz.name
    })

    val keyboardController = LocalSoftwareKeyboardController.current


    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = quizName,
            onValueChange = { quizName = it },
            label = {
                Text(text = "Quiz name")
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = { keyboardController?.hide() },
                onGo = {},
                onNext = {},
                onPrevious = {},
                onSearch = {},
                onSend = {})
        )

        SimpleButton(onClick = {
            onConfirmQuizClick(quiz.copy(name = quizName))
        }) {
            Text(text = "Confirm")
        }
    }
}

@PreviewDuo
@Composable
private fun QuizEditPreview() {
    PreviewContainer {
        QuizEditImpl(
            quizEditState = QuizEditState.mock(),
            onBackArrowClick = {},
            onRemoveClick = {},
            onEditWordClick = {},
            onEditQuizNameClick = {},
        )
    }
}