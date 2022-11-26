package com.ada.simplewords.feature.exercise

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ada.simplewords.common.OnClick
import com.ada.simplewords.common.OnClickTakes
import com.ada.simplewords.ui.components.utility.PreviewContainer

/* TODO Exercise screen.*/

@Composable
fun ExerciseScreen(quizId: Int?) {
    // TODO viewModel stuff.

    val viewModel = hiltViewModel<ExerciseScreenViewModel>()
    val exerciseScreenState = viewModel.exerciseScreenState
    LaunchedEffect(key1 = quizId, block = {
        if (quizId != null) {
            viewModel.getTranslationsForId(quizId)
        }
    })

    ExerciseScreenImpl(
        exerciseScreenState = exerciseScreenState,
        onValidate = { viewModel.validate(it) },
        onNextClick = { viewModel.next() }
    )
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
                question = exerciseScreenState.currentWordTranslationModel?.word
                    ?: "Couldn't load word :(",
                repeats = exerciseScreenState.currentWordTranslationModel?.repeat ?: 0
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
            AnswerCard(onValidate = {
                if (exerciseScreenState.validationState == ValidationState.CORRECT)
                    onNextClick()
                else
                    onValidate(it)
            })
        }
    }
}

@Composable
private fun QuestionCard(question: String, repeats: Int) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(modifier = Modifier.align(Alignment.Center), text = question)
        Text(modifier = Modifier.align(Alignment.BottomStart), text = "Repeats: $repeats")
    }
}

@Composable
private fun AnswerCard(onValidate: OnClickTakes<String>) {
    var inputText by remember {
        mutableStateOf("")
    }
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Box(modifier = Modifier.weight(1f)) {
            OutlinedTextField(modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
                value = inputText,
                onValueChange = { inputText = it },
                label = { Text(text = "Answer") })
        }
        Button(onClick = { onValidate(inputText) }) {
            Text(text = "Submit")
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Preview
@Composable
private fun ExerciseScreenPreview() {
    PreviewContainer() {
        ExerciseScreenImpl(
            exerciseScreenState = ExerciseScreenState.mock(),
            onValidate = {},
            onNextClick = {})
    }
}