package com.ada.quizdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ada.common.Key
import com.ada.common.debugLog
import com.ada.data.model.QuizMode
import com.ada.domain.model.Quiz
import com.ada.domain.model.WordTranslation
import com.ada.domain.usecases.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val PREFIX = "QuizDetailsViewModel:"

@HiltViewModel
class QuizDetailsViewModel @Inject constructor(
    private val observeQuizUseCase: ObserveQuizUseCase,
    private val observeWordsUseCase: ObserveWordsUseCase,
    private val updateWordUseCase: UpdateWordUseCase,
    private val updateQuizUseCase: UpdateQuizUseCase
) : ViewModel() {

    private var wordsJob: Job? = null
    private var quizJob: Job? = null

    private val _words = mutableMapOf<Key, WordTranslation>()
    private var _quiz: Quiz? = null
    private val _quizDetailsState =
        MutableStateFlow(QuizDetailsState.empty().copy(words = _words.toList().sortedByWord()))

    val quizDetailsState get() = _quizDetailsState.asStateFlow()

    fun observeQuiz(quizId: Key) {
        quizJob?.cancel()
        quizJob = viewModelScope.launch(Dispatchers.IO) {
            observeQuizUseCase.invoke(quizId = quizId).collect { quiz: Quiz ->
                debugLog { "ObserveQuiz: got: $quiz" }
                _quizDetailsState.update {
                    _quiz = quiz
                    it.copy(quiz = quiz)
                }
            }
        }
    }

    fun observeWords(quizId: String) {
        wordsJob?.cancel()
        _words.clear()
        wordsJob = viewModelScope.launch(Dispatchers.IO) {
            observeWordsUseCase(quizId = quizId).collect { wordResult ->
                debugLog { "$PREFIX Received: $wordResult" }

                when (wordResult.event) {
                    Event.Added -> {
                        _words[wordResult.word.first] = wordResult.word.second
                        _quizDetailsState.update {
                            it.copy(words = _words.toList().sortedByWord())
                        }
                    }
                    Event.Changed -> {
                        _words[wordResult.word.first] = wordResult.word.second
                        _quizDetailsState.update {
                            it.copy(words = _words.toList().sortedByWord())
                        }
                    }
                    Event.Removed -> {
                        _words.remove(wordResult.word.first)
                        _quizDetailsState.update {
                            it.copy(words = _words.toList().sortedByWord())
                        }
                    }
                    Event.Moved -> {
                        // not used
                    }
                }
            }
        }
    }

    fun restartQuiz() = viewModelScope.launch {
        _words.forEach {
            updateWordUseCase.invoke(it.value.copy(repeat = 3, isLearned = false))
        }
        _quiz?.let {
            updateQuizUseCase.invoke(
                it.copy(
                    wordsNumber = _words.size,
                    completedWords = 0
                )
            )
        }
    }

    fun changeMode() {
        _quiz?.let {
            updateQuizUseCase.invoke(
                it.copy(
                    mode = when (it.mode) {
                        QuizMode.Classic -> QuizMode.Modern
                        QuizMode.Modern -> QuizMode.Classic
                    }
                )
            )
        }
    }

    /**
     * Sorts words by learned state and name.
     */
    private fun List<Pair<Key, WordTranslation>>.sortedByWord() = this.sortedWith(
        compareBy({ it.second.isLearned }, { it.second.word })
    )
}