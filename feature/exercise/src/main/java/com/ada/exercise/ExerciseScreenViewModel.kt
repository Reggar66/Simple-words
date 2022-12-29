package com.ada.exercise

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ada.common.Key
import com.ada.common.debugLog
import com.ada.domain.mapper.toWordTranslationOrEmpty
import com.ada.domain.model.Quiz
import com.ada.domain.model.WordTranslation
import com.ada.data.model.WordTranslationModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseScreenViewModel @Inject constructor(
    private val observeWordsUseCase: com.ada.domain.usecases.ObserveWordsUseCase,
    private val updateWordUseCase: com.ada.domain.usecases.UpdateWordUseCase,
    private val observeQuizUseCase: com.ada.domain.usecases.ObserveQuizUseCase,
    private val updateQuizUseCase: com.ada.domain.usecases.UpdateQuizUseCase
) : ViewModel() {

    // TODO: Rewrite to use MutableStateFlow.
    private var quiz: Quiz? = null
    private val words = mutableMapOf<Key, WordTranslation>()

    var exerciseScreenState by
    mutableStateOf(ExerciseScreenState(getTranslation(), ValidationState.WAITING))

    fun observeQuiz(quizId: Key) = viewModelScope.launch {
        observeQuizUseCase.invoke(quizId).collect {
            quiz = it
        }
    }

    fun observeTranslations(quizId: Key) = viewModelScope.launch {
        debugLog { "observeTranslations: Start" }
        observeWordsUseCase.invoke(quizId = quizId).collect {
            when (it.event) {
                com.ada.domain.usecases.Event.Added -> {
                    words[it.word.first] = it.word.second
                    next()
                }
                com.ada.domain.usecases.Event.Changed -> {
                    debugLog { "Before: ${words[it.word.first]} | ${it.word.second}" }
                    words[it.word.first] = it.word.second
                    debugLog { "After: ${words[it.word.first]} | ${it.word.second}" }
                    updateQuiz()
                }
                else -> {
                    /*Nothing*/
                }
            }
        }
        debugLog { "observeTranslations: End" }
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

    fun next() {
        exerciseScreenState = if (isAllLearned())
            exerciseScreenState.copy(completed = true)
        else {
            exerciseScreenState.copy(
                currentWordTranslation = getTranslation(),
                validationState = ValidationState.WAITING
            )
        }
    }

    private fun updateQuiz() {
        val completedWords = countCompletedWords()
        if (quiz?.completedWords != completedWords)
            quiz?.let {
                updateQuizUseCase.invoke(it.copy(completedWords = completedWords))
            }
    }

    private fun getTranslation(): WordTranslation? {
        return if (hasNotLearnedWords())
            words.toList().filter { !it.second.isLearned && it.second.repeat > 0 }.random().second
        else null
    }

    private fun hasNotLearnedWords(): Boolean {
        words.toList().forEach {
            if (!it.second.isLearned && it.second.repeat > 0)
                return true
        }
        return false
    }

    private fun alterRepeats() {
        val currentWord = exerciseScreenState.currentWordTranslation
        currentWord?.let {
            val repeats = if (currentWord.repeat <= 0) 0 else currentWord.repeat - 1
            updateWordUseCase.invoke(currentWord.copy(repeat = repeats, isLearned = repeats == 0))
        }
    }

    private fun isAllLearned(): Boolean {
        return words.size == countCompletedWords()
    }

    private fun countCompletedWords() = words.toList().count { it.second.isLearned }

    private fun String.isMatchingTranslation() =
        exerciseScreenState.currentWordTranslation?.translation?.lowercase() == this.lowercase()
            .trim()
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