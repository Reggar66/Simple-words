package com.ada.exercise

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ada.common.OnClick
import com.ada.common.OnClickTakes
import com.ada.ui.PreviewContainer
import com.ada.ui.PreviewDuo
import com.ada.ui.theme.exerciseWord
import com.ada.ui.theme.itemBackground


/* TODO Show completed message */

@Composable
fun ExerciseScreen(quizId: String?, onReturnClick: OnClick) {
    val viewModel = hiltViewModel<ExerciseScreenViewModel>()
    val exerciseScreenState by viewModel.exerciseState.collectAsState()

    LaunchedEffect(key1 = quizId, block = {
        quizId?.let {
            viewModel.observeQuiz(quizId = it)
            viewModel.observeTranslations(quizId = it)
        }
    })

    if (exerciseScreenState.completed)
        CompletedPanel(onReturnClick = onReturnClick)
    else
        ExerciseScreenImpl(
            exerciseScreenState = exerciseScreenState,
            onValidate = { viewModel.validate(it) },
            onNextClick = { viewModel.next() }
        )
}

@Composable
private fun CompletedPanel(onReturnClick: OnClick) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.weight(1f)) {
            Card(
                modifier = Modifier.align(Alignment.Center),
                backgroundColor = MaterialTheme.colors.itemBackground
            ) {
                Text(
                    modifier = Modifier.padding(32.dp),
                    text = "Exercise completed, \ncongratulations!",
                    textAlign = TextAlign.Center
                )
            }
        }

        Button(onClick = onReturnClick, shape = CircleShape) {
            Text(text = "Return to quizzes")
        }
    }
}

@Composable
private fun ExerciseScreenImpl(
    exerciseScreenState: ExerciseScreenState,
    onValidate: OnClickTakes<String> = {},
    onNextClick: OnClick
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Card(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            QuestionCard(
                question = exerciseScreenState.currentWordTranslation?.word
                    ?: "Couldn't load word :(",
                repeats = exerciseScreenState.currentWordTranslation?.repeat ?: 0
            )
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.offset(),
                text = when (exerciseScreenState.validationState) {
                    ValidationState.CORRECT -> "Correct!"
                    ValidationState.WRONG -> "Wrong."
                    ValidationState.WAITING -> "What's the answer?"
                },
                color = when (exerciseScreenState.validationState) {
                    ValidationState.CORRECT -> Color.Green // TODO material theme
                    ValidationState.WRONG -> Color.Red // TODO material theme
                    ValidationState.WAITING -> MaterialTheme.colors.onSurface
                }
            )
        }
        Card(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            AnswerCard(
                validationState = exerciseScreenState.validationState,
                onValidate = {
                    onValidate(it)
                },
                onNextClick = onNextClick
            )
        }
    }
}

@Composable
private fun QuestionCard(question: String, repeats: Int) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 8.dp),
            text = question,
            style = MaterialTheme.typography.exerciseWord
        )
        Text(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(8.dp),
            text = "Repeats: $repeats"
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun AnswerCard(
    validationState: ValidationState,
    onValidate: OnClickTakes<String>,
    onNextClick: OnClick
) {
    var inputText by remember {
        mutableStateOf("")
    }

    LaunchedEffect(key1 = validationState, block = {
        if (validationState == ValidationState.WAITING)
            inputText = ""
    })

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Box(modifier = Modifier
            .weight(1f)
            .onKeyEvent {
                // Applies answer by pressing enter key on physical keyboard.
                if (it.key == Key.Enter) {
                    onValidate(inputText)
                    true
                } else false
            }) {
            if (validationState == ValidationState.WAITING)
                AnswerInput(
                    inputText = inputText,
                    onValueChanged = { inputText = it },
                    onDone = { onValidate(inputText) })
            else
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                        .padding(horizontal = 8.dp),
                    text = inputText,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.exerciseWord
                )
        }

        if (validationState != ValidationState.WAITING)
            Button(onClick = { onNextClick() }) {
                Text(text = "Next") // TODO: strings.xml
            }
    }
}

@Composable
private fun BoxScope.AnswerInput(
    inputText: String,
    onValueChanged: (String) -> Unit,
    onDone: (KeyboardActionScope.() -> Unit)
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .align(Alignment.Center),
        value = inputText,
        onValueChange = onValueChanged,
        label = { Text(text = "Answer") }, // TODO: strings.xml
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = onDone,
            onGo = {},
            onNext = {},
            onPrevious = {},
            onSearch = {},
            onSend = {}
        )
    )
}

@PreviewDuo
@Composable
private fun ExerciseScreenPreview() {
    PreviewContainer {
        ExerciseScreenImpl(
            exerciseScreenState = ExerciseScreenState.mock(),
            onValidate = {},
            onNextClick = {})
    }
}

@PreviewDuo
@Composable
private fun ExerciseScreenCompletedPreview() {
    PreviewContainer {
        CompletedPanel() {}
    }
}