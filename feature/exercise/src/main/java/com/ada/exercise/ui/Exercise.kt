package com.ada.exercise.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ada.common.OnClick
import com.ada.common.OnClickTakes
import com.ada.data.model.QuizMode
import com.ada.data.model.WordTranslationModel
import com.ada.domain.mapper.toWordTranslationOrEmpty
import com.ada.exercise.ExerciseScreenState
import com.ada.exercise.ValidationState
import com.ada.ui.PreviewContainer
import com.ada.ui.PreviewDuo

@Composable
internal fun Exercise(
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
            QuestionPanel(
                modifier = Modifier.fillMaxSize(),
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
                    ValidationState.CORRECT -> "Correct!" // TODO: strings.xml
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

            when (exerciseScreenState.mode) {
                QuizMode.Classic -> {
                    AnswerPanelClassic(
                        Modifier.fillMaxSize(),
                        validationState = exerciseScreenState.validationState,
                        onValidate = {
                            onValidate(it)
                        },
                        onNextClick = onNextClick
                    )
                }
                QuizMode.Modern -> {
                    AnswerPanelModern(
                        modifier = Modifier.weight(1f),
                        validationState = exerciseScreenState.validationState,
                        items = exerciseScreenState.answerOptions,
                        onItemClick = { onValidate(it.translation) },
                        onNextClick = onNextClick
                    )
                }
            }
        }
    }
}

@PreviewDuo
@Composable
private fun ExerciseScreenPreview() {
    PreviewContainer {
        Exercise(
            exerciseScreenState = ExerciseScreenState.mock(),
            onValidate = {},
            onNextClick = {})
    }
}

@PreviewDuo
@Composable
private fun ExerciseScreenPreview2() {
    PreviewContainer {
        Exercise(
            exerciseScreenState = ExerciseScreenState.mock().copy(mode = QuizMode.Modern),
            onValidate = {},
            onNextClick = {})
    }
}