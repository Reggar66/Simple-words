package com.ada.simplewords.feature.quiz.list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.ada.simplewords.data.QuizData

class QuizListViewModel : ViewModel() {

    var quizListState by mutableStateOf(QuizListScreenState())
        private set

    private val quizzes: List<QuizData> = QuizData.mock // TODO fetch with repository

    init {
        quizListState = QuizListScreenState(quizzes = quizzes)
        sortByName()
    }

    fun sortByName() {
        quizListState =
            QuizListScreenState(
                quizzes = quizzes.sortedWith(
                    // Sorts by completion first then by name, so completed quizzes are at the bottom.
                    compareBy(
                        { it.words.size == it.learnedWords.size },
                        { it.quizItem.name },
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