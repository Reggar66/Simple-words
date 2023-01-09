package com.ada.quizedit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ada.common.Key
import com.ada.data.model.QuizModel
import com.ada.data.model.WordTranslationModel
import com.ada.domain.mapper.toQuizOrEmpty
import com.ada.domain.mapper.toWordTranslationOrEmpty
import com.ada.domain.model.Quiz
import com.ada.domain.model.WordTranslation
import com.ada.domain.usecases.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class QuizEditViewModel @Inject constructor(
    private val observeWordsUseCase: ObserveWordsUseCase,
    private val updateWordUseCase: UpdateWordUseCase,
    private val observeQuizUseCase: ObserveQuizUseCase,
    private val updateQuizUseCase: UpdateQuizUseCase,
    private val removeWordUseCase: RemoveWordUseCase
) : ViewModel() {

    private var wordsJob: Job? = null
    private var quizJob: Job? = null

    private var quiz: Quiz? = null
    private val words = mutableMapOf<Key, WordTranslation>()

    private var wordToDelete: WordTranslation? = null

    private val _quizEditState =
        MutableStateFlow(QuizEditState.empty().copy(words = words.toWordList()))

    val quizEditState get() = _quizEditState.asStateFlow()

    fun observeQuiz(quizId: String) {
        quizJob?.cancel()
        quizJob = viewModelScope.launch {
            observeQuizUseCase.invoke(quizId).collect { _quiz ->
                quiz = _quiz
                _quizEditState.update {
                    it.copy(quiz = _quiz)
                }
            }
        }
    }

    fun observeTranslations(quizId: String) {
        wordsJob?.cancel()
        wordsJob = viewModelScope.launch {
            observeWordsUseCase.invoke(quizId = quizId).collect { wordResult ->
                when (wordResult.event) {
                    Event.Added -> {
                        words[wordResult.word.first] = wordResult.word.second

                        if (words.size == quiz?.wordsNumber)
                            _quizEditState.update {
                                it.copy(words = words.toWordList())
                            }
                    }
                    Event.Changed -> {
                        words[wordResult.word.first] = wordResult.word.second
                        _quizEditState.update {
                            it.copy(words = words.toWordList())
                        }
                    }
                    Event.Removed -> {
                        words.remove(wordResult.word.first)
                        _quizEditState.update {
                            it.copy(words = words.toWordList())
                        }
                    }
                    else -> {
                        // rest is not used
                    }
                }
            }
        }
    }

    fun markWordForDeletion(word: WordTranslation) {
        wordToDelete = word
    }

    fun deleteMarkedWord() = viewModelScope.launch(Dispatchers.IO) {
        quiz?.let { quiz ->
            wordToDelete?.let { word ->
                if (removeWordUseCase.invoke(quizId = quiz.id, wordId = word.id)) {
                    wordToDelete = null
                    updateQuizUseCase.invoke(
                        quiz = quiz.copy(
                            wordsNumber = quiz.wordsNumber - 1,
                            completedWords = words.count { it.value.isLearned }
                        )
                    )
                }
            }
        }
    }

    fun setWordToEdit(word: WordTranslation) {
        _quizEditState.update {
            it.copy(wordToEdit = word)
        }
    }

    fun updateWordTranslation(newWordTranslation: WordTranslation) {
        updateWordUseCase.invoke(wordTranslation = newWordTranslation)
    }

    fun updateQuiz(newQuiz: Quiz) {
        updateQuizUseCase.invoke(quiz = newQuiz)
    }

    fun changeEditMode(editMode: EditMode) {
        _quizEditState.update { it.copy(editMode = editMode) }
    }
}


private fun MutableMap<Key, WordTranslation>.toWordList() = map { it.value }

data class QuizEditState(
    val quiz: Quiz,
    val words: List<WordTranslation>,
    val wordToEdit: WordTranslation? = null,
    val editMode: EditMode = EditMode.Quiz
) {
    companion object {
        fun empty() = QuizEditState(
            quiz = Quiz.empty(),
            words = emptyList()
        )

        fun mock() = QuizEditState(
            quiz = QuizModel.mockAnimals.toQuizOrEmpty(),
            words = WordTranslationModel.mockAnimals.map {
                it.toWordTranslationOrEmpty()
            }
        )
    }
}

enum class EditMode {
    Quiz,
    Word
}