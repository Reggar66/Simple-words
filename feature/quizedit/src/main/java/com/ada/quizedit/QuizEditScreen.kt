package com.ada.quizedit

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ada.common.OnClick
import com.ada.common.OnClickTakes
import com.ada.common.SimpleNavigation
import com.ada.domain.model.WordTranslation
import com.ada.quizlist.DeleteBottomSheet
import com.ada.ui.PreviewContainer
import com.ada.ui.PreviewDuo
import com.ada.ui.components.SimpleModalBottomSheetLayout
import com.ada.ui.components.SwipeMenu
import com.ada.ui.components.TopBar
import com.ada.ui.components.WordItem
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
        QuizEdit(
            quizEditState = quizEditState,
            onBackArrowClick = closeScreen,
            onRemoveClick = {
                wordToDeleteName = "${it.word} | ${it.translation}"
                viewModel.markWordForDeletion(it)
                scope.launch {
                    modalSheetState.show()
                }
            },
            onEditClick = {

            }
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun QuizEdit(
    quizEditState: QuizEditState,
    onBackArrowClick: OnClick,
    onRemoveClick: OnClickTakes<WordTranslation>,
    onEditClick: OnClickTakes<WordTranslation>
) {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        TopBar(onBackArrowClick = onBackArrowClick) {
            Text(
                text = "Quiz Edit", // TODO: strings
                style = MaterialTheme.typography.topBarTitle
            )
        }

        Text(
            modifier = Modifier.padding(vertical = 8.dp),
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
                    onEditClick = { onEditClick(wordItem) }) {
                    WordItem(
                        leftText = wordItem.word,
                        rightText = wordItem.translation
                    )
                }
            }
        }
    }
}

@PreviewDuo
@Composable
private fun QuizEditPreview() {
    PreviewContainer {
        QuizEdit(
            quizEditState = QuizEditState.mock(),
            onBackArrowClick = {},
            onRemoveClick = {},
            onEditClick = {})
    }
}