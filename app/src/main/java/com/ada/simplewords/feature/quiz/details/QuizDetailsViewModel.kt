package com.ada.simplewords.feature.quiz.details

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ada.simplewords.common.Key
import com.ada.simplewords.common.debugLog
import com.ada.simplewords.data.Quiz
import com.ada.simplewords.data.WordTranslation
import com.ada.simplewords.domain.usecases.Event
import com.ada.simplewords.domain.usecases.ObserveWordsUseCase
import com.ada.simplewords.domain.usecases.ObserveQuizUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
) : ViewModel() {

    private val _words = mutableStateMapOf<Key, WordTranslation>()
    private val _quizDetailsState = MutableStateFlow(QuizDetailsState.empty().copy(words = _words))
    val quizDetailsState get() = _quizDetailsState.asStateFlow()


    fun observeQuiz(quizId: Key) = viewModelScope.launch(Dispatchers.IO) {
        observeQuizUseCase.invoke(quizId = quizId).collect { quiz: Quiz ->
            debugLog { "ObserveQuiz: got: $quiz" }
            _quizDetailsState.update {
                it.copy(quiz = quiz)
            }
        }
    }

    fun observeWords(quizId: String) = viewModelScope.launch(Dispatchers.IO) {
        _words.clear()
        observeWordsUseCase(quizId = quizId).collect {
            debugLog { "$PREFIX Received: $it" }

            when (it.event) {
                Event.Added -> {
                    _words[it.word.first] = it.word.second
                }
                Event.Changed -> {/* TODO */
                }
                Event.Removed -> {
                    _words.remove(it.word.first)
                }
                Event.Moved -> {/* TODO */
                }
            }
        }
    }
}