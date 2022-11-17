package com.example.simplewords.feature.exercise

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.simplewords.domain.models.WordTranslation
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ExerciseScreenViewModel @Inject constructor(private val fakeRepo: FakeRepo) : ViewModel() {

    private var translations: List<WordTranslation> = WordTranslation.mockAnimals

    var exerciseScreenState by
    mutableStateOf(ExerciseScreenState(drawTranslation(), ValidationState.WAITING))

    private fun drawTranslation(): WordTranslation? {
        while (hasNotLearnedWords()) {
            val word = translations.random()
            if (word.repeat > 0)
                return word
        }
        return null
    }

    private fun hasNotLearnedWords(): Boolean {
        translations.forEach {
            if (!it.isLearned)
                return true
        }
        return false
    }

    fun validate(answer: String) {
        exerciseScreenState = when {
            answer.isMatchingTranslation() -> {
                translations = translations.map {
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

    fun next() {
        exerciseScreenState = exerciseScreenState.copy(
            currentWordTranslation = drawTranslation(),
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
            currentWordTranslation = WordTranslation.mockAnimals.first(),
            validationState = ValidationState.WAITING
        )
    }
}

enum class ValidationState {
    CORRECT,
    WRONG,
    WAITING
}