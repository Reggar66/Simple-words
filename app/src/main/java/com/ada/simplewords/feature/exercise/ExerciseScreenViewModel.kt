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
import com.ada.simplewords.domain.usecases.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseScreenViewModel @Inject constructor(
    private val getWordsUseCase: GetWordsUseCase,
    private val observeWordsUseCase: ObserveWordsUseCase,
    private val updateWordUseCase: UpdateWordUseCase
) : ViewModel() {

    private val words = mutableMapOf<Key, WordTranslation>()

    var exerciseScreenState by
    mutableStateOf(ExerciseScreenState(getTranslation(), ValidationState.WAITING))

    fun observeTranslations(quizId: String) = viewModelScope.launch {
        debugLog { "observeTranslations: Start" }
        observeWordsUseCase.invoke(quizId = quizId).collect {
            when (it.event) {
                Event.Added -> {
                    words[it.word.first] = it.word.second
                    next()
                }
                Event.Changed -> {
                    debugLog { "Before: ${words[it.word.first]} | ${it.word.second}" }
                    words[it.word.first] = it.word.second
                    debugLog { "After: ${words[it.word.first]} | ${it.word.second}" }
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
        val currentWord = exerciseScreenState.currentWordTranslation
        currentWord?.let {
            val repeats = if (currentWord.repeat <= 0) 0 else currentWord.repeat - 1
            updateWordUseCase.invoke(currentWord.copy(repeat = repeats, isLearned = repeats == 0))
        }
    }

    private fun isAllLearned(): Boolean {
        return words.size == words.toList().count { it.second.isLearned }
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
    val validationState: ValidationState,
    val completed: Boolean = false
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