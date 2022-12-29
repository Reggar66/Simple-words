package com.ada.quizlist

import com.ada.domain.model.Quiz

data class QuizListScreenState(
    val quizzes: List<Quiz> = emptyList(),
    val currentlySelectedQuiz: Quiz? = null
)