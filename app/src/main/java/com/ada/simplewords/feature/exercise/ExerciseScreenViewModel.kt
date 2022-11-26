package com.ada.simplewords.feature.exercise

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.ada.simplewords.data.QuizData
import com.ada.simplewords.domain.models.WordTranslationModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ExerciseScreenViewModel @Inject constructor() : ViewModel() {

    private var translations: List<WordTranslationModel>? = null

    var exerciseScreenState by
    mutableStateOf(ExerciseScreenState(getTranslation(), ValidationState.WAITING))

    fun getTranslationsForId(quizId: Int) {
        translations = QuizData.mock.find {
            it.quizItemModel.id == quizId.toString()
        }?.words

        exerciseScreenState = ExerciseScreenState(
            currentWordTranslationModel = getTranslation(),
            validationState = ValidationState.WAITING
        )
    }

    // TODO This get translation even if repeats is lower than 0. Change it.
    private fun getTranslation(): WordTranslationModel? {
        return if (hasNotLearnedWords())
            translations?.random()
        else null
    }

    private fun hasNotLearnedWords(): Boolean {
        translations?.forEach {
            if (!it.isLearned)
                return true
        }
        return false
    }

    fun validate(answer: String) {
        exerciseScreenState = when {
            answer.isMatchingTranslation() -> {
                translations = translations?.map {
                    if (it.id == exerciseScreenState.currentWordTranslationModel?.id) it.copy(
                        repeat = it.repeat - 1
                    ) else
                        it
                }
                exerciseScreenState.copy(validationState = ValidationState.CORRECT)
            }

            else -> exerciseScreenState.copy(validationState = ValidationState.WRONG)
        }
    }

    private fun String.isMatchingTranslation() =
        exerciseScreenState.currentWordTranslationModel?.translation?.lowercase() == this.lowercase()
            .trim()

    fun next() {
        exerciseScreenState = exerciseScreenState.copy(
            currentWordTranslationModel = getTranslation(),
            validationState = ValidationState.WAITING
        )
    }
}

data class ExerciseScreenState(
    val currentWordTranslationModel: WordTranslationModel?,
    val validationState: ValidationState
) {
    companion object {
        fun mock() = ExerciseScreenState(
            currentWordTranslationModel = WordTranslationModel.mockAnimals.first(),
            validationState = ValidationState.WAITING
        )
    }
}

enum class ValidationState {
    CORRECT,
    WRONG,
    WAITING
}