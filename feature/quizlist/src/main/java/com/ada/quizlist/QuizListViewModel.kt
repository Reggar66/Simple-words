package com.ada.quizlist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ada.common.debugLog
import com.ada.domain.model.Quiz
import com.ada.data.repositories.FirebaseRepository
import com.ada.domain.usecases.GetQuizzesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizListViewModel @Inject constructor(
    val firebaseRepository: FirebaseRepository,
    private val getQuizzesUseCase: GetQuizzesUseCase
) : ViewModel() {

    var quizListState by mutableStateOf(QuizListScreenState())
        private set

    private var quizzes: List<Quiz> = emptyList()

    init {
        fetchQuizzes()
    }

    private fun fetchQuizzes() = viewModelScope.launch {
        debugLog { "fetchQuizzes: launched." }
        getQuizzesUseCase().collect {
            debugLog { "fetchQuizzes: downloaded: $it" }

            quizzes = it
            sortByName()
        }
        debugLog { "fetchQuizzes: ended." }
    }

    private fun sortByName() {
        quizListState = QuizListScreenState(
            quizzes = quizzes.sortedWith(
                // Sorts by completion first then by name, so completed quizzes are at the bottom.
                compareBy(
                    { it.wordsNumber == it.completedWords },
                    { it.name },
                )
            )
        )
    }

    fun selectQuiz(quizData: Quiz) {
        quizListState = quizListState.copy(currentlySelectedQuiz = quizData)
    }

    fun shuffle() {
        quizListState = quizListState.copy(quizzes = quizzes.shuffled())
    }
}

data class QuizListScreenState(
    val quizzes: List<Quiz> = emptyList(),
    val currentlySelectedQuiz: Quiz? = null
)