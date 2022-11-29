package com.ada.simplewords.feature.quiz.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ada.simplewords.common.debugLog
import com.ada.simplewords.data.WordTranslation
import com.ada.simplewords.domain.usecases.Event
import com.ada.simplewords.domain.usecases.ObserveWordsUseCase
import com.ada.simplewords.domain.usecases.GetWordsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val PREFIX = "QuizDetailsViewModel:"

@HiltViewModel
class QuizDetailsViewModel @Inject constructor(
    private val getWordsUseCase: GetWordsUseCase,
    private val observeWordsUseCase: ObserveWordsUseCase
) :
    ViewModel() {

    var words by mutableStateOf<List<WordTranslation>>(emptyList())

    var hashWords = mutableStateMapOf<String, WordTranslation>()

    fun getTranslationsForId(quizId: String) = viewModelScope.launch {
        getWordsUseCase(quizId = quizId).collect {
            words = it
        }
    }

    fun observeWords(quizId: String) = viewModelScope.launch {
        hashWords.clear()
        observeWordsUseCase(quizId = quizId).collect {
            debugLog { "$PREFIX Received: $it" }

            when (it.event) {
                Event.Added -> hashWords[it.word.first] = it.word.second
                Event.Changed -> {/* TODO */
                }
                Event.Removed -> hashWords.remove(it.word.first)
                Event.Moved -> {/* TODO */
                }
            }
        }
    }
}