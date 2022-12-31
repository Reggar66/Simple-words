package com.ada.exercise

import com.ada.data.model.QuizMode
import com.ada.data.model.WordTranslationModel
import com.ada.domain.mapper.toWordTranslationOrEmpty
import com.ada.domain.model.WordTranslation

data class ExerciseScreenState(
    val mode: QuizMode = QuizMode.Classic,
    val currentWordTranslation: WordTranslation?,
    val validationState: ValidationState,
    val completed: Boolean = false,
    val answerOptions: List<WordTranslation>
) {
    companion object {
        fun mock() = ExerciseScreenState(
            currentWordTranslation = WordTranslationModel.mockAnimals.first()
                .toWordTranslationOrEmpty(),
            validationState = ValidationState.WAITING,
            answerOptions = WordTranslationModel.mockAnimals.map { it.toWordTranslationOrEmpty() }
        )
    }
}