package com.ada.exercise

import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.ada.common.OnClick
import com.ada.exercise.ui.CompletedPanel
import com.ada.exercise.ui.Exercise

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
        Exercise(
            exerciseScreenState = exerciseScreenState,
            onValidate = { viewModel.validate(it) },
            onNextClick = { viewModel.next() }
        )
}

