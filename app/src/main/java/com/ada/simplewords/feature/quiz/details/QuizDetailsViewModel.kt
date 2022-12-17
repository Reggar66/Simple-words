package com.ada.simplewords.feature.quiz.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ada.common.Key
import com.ada.common.debugLog
import com.ada.domain.model.Quiz
import com.ada.domain.model.WordTranslation
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
    private val observeQuizUseCase: com.ada.domain.usecases.ObserveQuizUseCase,
    private val observeWordsUseCase: com.ada.domain.usecases.ObserveWordsUseCase,
    private val updateWordUseCase: com.ada.domain.usecases.UpdateWordUseCase,
    private val updateQuizUseCase: com.ada.domain.usecases.UpdateQuizUseCase
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
                    com.ada.domain.usecases.Event.Added -> {
                        _words[wordResult.word.first] = wordResult.word.second
                        _quizDetailsState.update {
                            it.copy(words = _words.toList().sortedByWord())
                        }
                    }
                    com.ada.domain.usecases.Event.Changed -> {/* TODO */
                    }
                    com.ada.domain.usecases.Event.Removed -> {
                        _words.remove(wordResult.word.first)
                        _quizDetailsState.update {
                            it.copy(words = _words.toList().sortedByWord())
                        }
                    }
                    com.ada.domain.usecases.Event.Moved -> {/* TODO */
                    }
                }
            }
        }
    }

    private fun List<Pair<Key, WordTranslation>>.sortedByWord() = this.sortedBy { it.second.word }

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
}