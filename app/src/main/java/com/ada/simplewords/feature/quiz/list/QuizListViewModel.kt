package com.ada.simplewords.feature.quiz.list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ada.simplewords.common.debugLog
import com.ada.data.Quiz
import com.ada.simplewords.domain.repositories.FirebaseRepository
import com.ada.simplewords.domain.usecases.GetQuizzesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizListViewModel @Inject constructor(
    val firebaseRepository: FirebaseRepository, private val getQuizzesUseCase: GetQuizzesUseCase
) : ViewModel() {

    var quizListState by mutableStateOf(QuizListScreenState())
        private set

    private var quizzes: List<com.ada.data.Quiz> = emptyList()

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

    fun selectQuiz(quizData: com.ada.data.Quiz) {
        quizListState = quizListState.copy(currentlySelectedQuiz = quizData)
    }

    fun shuffle() {
        quizListState = quizListState.copy(quizzes = quizzes.shuffled())
    }
}

data class QuizListScreenState(
    val quizzes: List<com.ada.data.Quiz> = emptyList(),
    val currentlySelectedQuiz: com.ada.data.Quiz? = null
)