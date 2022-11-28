package com.ada.simplewords.feature.exercise

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ada.simplewords.common.debugLog
import com.ada.simplewords.data.WordTranslation
import com.ada.simplewords.data.toWordTranslationOrEmpty
import com.ada.simplewords.domain.models.WordTranslationModel
import com.ada.simplewords.domain.usecases.Event
import com.ada.simplewords.domain.usecases.GetWordsByOneUseCase
import com.ada.simplewords.domain.usecases.GetWordsUseCase
import com.ada.simplewords.domain.usecases.Key
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseScreenViewModel @Inject constructor(
    private val getWordsUseCase: GetWordsUseCase,
    private val getWordsByOneUseCase: GetWordsByOneUseCase
) : ViewModel() {

    private val words = mutableMapOf<Key, WordTranslation>()

    var exerciseScreenState by
    mutableStateOf(ExerciseScreenState(getTranslation(), ValidationState.WAITING))

    fun observeTranslations(quizId: String) = viewModelScope.launch {
        debugLog { "observeTranslations: Start" }
        getWordsByOneUseCase.invoke(quizId = quizId).collect {
            when (it.event) {
                Event.Added -> {
                    words[it.word.first] = it.word.second
                    next()
                }
                Event.Changed -> {
                    words[it.word.first] = it.word.second
                }
                else -> {
                    /*Nothing*/
                }
            }
        }
        debugLog { "observeTranslations: End" }
    }

    private fun getTranslation(): WordTranslation? {
        return if (hasNotLearnedWords())
            words.toList().filter { !it.second.isLearned && it.second.repeat > 0 }.random().second
        else null
    }

    private fun hasNotLearnedWords(): Boolean {
        words.toList().forEach {
            if (!it.second.isLearned)
                return true
        }
        return false
    }

    fun validate(answer: String) {
        exerciseScreenState = when {
            answer.isMatchingTranslation() -> {
                alterRepeats()
                exerciseScreenState.copy(validationState = ValidationState.CORRECT)
            }
            else -> exerciseScreenState.copy(validationState = ValidationState.WRONG)
        }
    }

    private fun alterRepeats() {
        /* TODO repeats-- and save to db. */
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