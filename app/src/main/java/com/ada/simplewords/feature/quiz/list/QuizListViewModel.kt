package com.ada.simplewords.feature.quiz.list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ada.simplewords.common.debugLog
import com.ada.simplewords.data.QuizData
import com.ada.simplewords.domain.models.QuizItemModel
import com.ada.simplewords.domain.repositories.FirebaseRepository
import com.ada.simplewords.domain.usecases.GetQuizzesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizListViewModel @Inject constructor(
    val firebaseRepository: FirebaseRepository,
    private val getQuizzesUseCase: GetQuizzesUseCase
) :
    ViewModel() {

    var quizListState by mutableStateOf(QuizListScreenState())
        private set

    private var quizzes: List<QuizData> = emptyList() // TODO fetch with repository

    init {
        fetchQuizzes()
    }

    private fun fetchQuizzes() = viewModelScope.launch {
        debugLog { "fetchQuizzes: launched." }
        getQuizzesUseCase().collect {
            debugLog { "fetchQuizzes: downloaded: $it" }

            val newQuizzes = mutableListOf<QuizData>()

            it.forEach {
                newQuizzes.add(
                    QuizData(
                        quizItemModel = it,
                        words = listOf(),
                        learnedWords = listOf()
                    )
                )
            }

            quizzes = newQuizzes
            sortByName()
        }
        debugLog { "fetchQuizzes: ended." }
    }

    fun sortByName() {
        quizListState =
            QuizListScreenState(
                quizzes = quizzes.sortedWith(
                    // Sorts by completion first then by name, so completed quizzes are at the bottom.
                    compareBy(
                        { it.words.size == it.learnedWords.size },
                        { it.quizItemModel.name },
                    )
                )
            )
    }

    fun selectQuiz(quizData: QuizData) {
        quizListState = quizListState.copy(currentlySelectedQuiz = quizData)
    }

    fun shuffle() {
        quizListState = quizListState.copy(quizzes = quizzes.shuffled())
    }
}

data class QuizListScreenState(
    val quizzes: List<QuizData> = emptyList(),
    val currentlySelectedQuiz: QuizData? = null
)