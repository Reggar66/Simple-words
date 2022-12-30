package com.ada.exercise

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ada.common.Key
import com.ada.common.debugLog
import com.ada.data.model.WordTranslationModel
import com.ada.domain.mapper.toWordTranslationOrEmpty
import com.ada.domain.model.Quiz
import com.ada.domain.model.WordTranslation
import com.ada.domain.usecases.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseScreenViewModel @Inject constructor(
    private val observeWordsUseCase: com.ada.domain.usecases.ObserveWordsUseCase,
    private val updateWordUseCase: com.ada.domain.usecases.UpdateWordUseCase,
    private val observeQuizUseCase: com.ada.domain.usecases.ObserveQuizUseCase,
    private val updateQuizUseCase: com.ada.domain.usecases.UpdateQuizUseCase
) : ViewModel() {

    private var quiz: Quiz? = null
    private val words = mutableMapOf<Key, WordTranslation>()

    var exerciseState = MutableStateFlow(
        ExerciseScreenState(
            currentWordTranslation = getTranslation(),
            validationState = ValidationState.WAITING
        )
    )
        private set

    fun observeQuiz(quizId: Key) = viewModelScope.launch {
        observeQuizUseCase.invoke(quizId).collect {
            quiz = it
        }
    }

    fun observeTranslations(quizId: Key) = viewModelScope.launch {
        debugLog { "observeTranslations: Start" }
        observeWordsUseCase.invoke(quizId = quizId).collect {
            when (it.event) {
                Event.Added -> {
                    words[it.word.first] = it.word.second

                    // Loads new word translation once all words are loaded.
                    if (words.size == quiz?.wordsNumber)
                        next()
                }
                Event.Changed -> {
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
        when {
            answer.isMatchingTranslation() -> {
                alterRepeats()
                exerciseState.update {
                    it.copy(validationState = ValidationState.CORRECT)
                }
            }
            else -> exerciseState.update {
                it.copy(validationState = ValidationState.WRONG)
            }

        }
    }

    fun next() {
        if (isAllLearned())
            exerciseState.update { it.copy(completed = true) }
        else
        exerciseState.update {
            it.copy(
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
        val currentWord = exerciseState.value.currentWordTranslation
        currentWord?.let {
            val repeats = if (currentWord.repeat <= 0) 0 else currentWord.repeat - 1
            exerciseState.update {
                it.copy(
                    currentWordTranslation = it.currentWordTranslation
                        ?.copy(repeat = repeats)
                )
            }
            updateWordUseCase.invoke(currentWord.copy(repeat = repeats, isLearned = repeats == 0))
        }
    }

    private fun isAllLearned(): Boolean {
        return words.size == countCompletedWords()
    }

    private fun countCompletedWords() = words.toList().count { it.second.isLearned }

    private fun String.isMatchingTranslation() =
        exerciseState.value.currentWordTranslation?.translation?.lowercase() == this.lowercase()
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