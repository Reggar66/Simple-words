package com.example.simplewords.feature.list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class QuizListViewModel : ViewModel() {

    var quizListState by mutableStateOf(QuizListScreenState())
        private set

    private val quizzes: List<QuizData> = QuizData.mock // TODO fetch with repository

    init {
        quizListState = QuizListScreenState(quizzes = quizzes)
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

    fun shuffle() {
        quizListState = QuizListScreenState(quizzes = quizzes.shuffled())
    }
}

data class QuizListScreenState(val quizzes: List<QuizData> = emptyList())