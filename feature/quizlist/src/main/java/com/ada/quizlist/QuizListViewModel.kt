package com.ada.quizlist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ada.common.debugLog
import com.ada.data.repositories.RealTimeDatabaseRepository
import com.ada.domain.model.Quiz
import com.ada.domain.usecases.ObserveCurrentUserUseCase
import com.ada.domain.usecases.GetQuizzesUseCase
import com.ada.domain.usecases.RemoveQuizWithWordsUseCase
import com.ada.domain.usecases.SignOutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizListViewModel @Inject constructor(
    private val getQuizzesUseCase: GetQuizzesUseCase,
    observeCurrentUserUseCase: ObserveCurrentUserUseCase,
    private val removeQuizWithWordsUseCase: RemoveQuizWithWordsUseCase
) : ViewModel() {

    private var quizzes: List<Quiz> = emptyList()
    private var quizToRemove: Quiz? = null

    var quizListState by mutableStateOf(QuizListScreenState())
        private set

    val user = observeCurrentUserUseCase.invoke()

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
        quizListState = quizListState.copy(
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

    fun setQuizForRemove(quiz: Quiz) {
        quizToRemove = quiz
    }

    fun removeQuiz() {
        quizToRemove?.let {
            removeQuizWithWordsUseCase.invoke(it)
        }
    }
}

