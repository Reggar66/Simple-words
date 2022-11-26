package com.ada.simplewords.feature.exercise

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ada.simplewords.data.WordTranslation
import com.ada.simplewords.data.toWordTranslationOrEmpty
import com.ada.simplewords.domain.models.WordTranslationModel
import com.ada.simplewords.domain.usecases.GetWordsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseScreenViewModel @Inject constructor(private val getWordsUseCase: GetWordsUseCase) :
    ViewModel() {

    private var translations: List<WordTranslation>? = null

    var exerciseScreenState by
    mutableStateOf(ExerciseScreenState(getTranslation(), ValidationState.WAITING))

    fun getTranslationsForId(quizId: String) = viewModelScope.launch {
        getWordsUseCase(quizId = quizId).collect {
            translations = it
        }
    }


    // TODO This get translation even if repeats is lower than 0. Change it.
    private fun getTranslation(): WordTranslation? {
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
                    if (it.id == exerciseScreenState.currentWordTranslation?.id) it.copy(
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
        exerciseScreenState.currentWordTranslation?.translation?.lowercase() == this.lowercase()
            .trim()

    fun next() {
        exerciseScreenState = exerciseScreenState.copy(
            currentWordTranslation = getTranslation(),
            validationState = ValidationState.WAITING
        )
    }
}

data class ExerciseScreenState(
    val currentWordTranslation: WordTranslation?,
    val validationState: ValidationState
) {
    companion object {
        fun mock() = ExerciseScreenState(
            currentWordTranslation = WordTranslationModel.mockAnimals.first()
                .toWordTranslationOrEmpty(),
            validationState = ValidationState.WAITING
        )
    }
}

enum class ValidationState {
    CORRECT,
    WRONG,
    WAITING
}