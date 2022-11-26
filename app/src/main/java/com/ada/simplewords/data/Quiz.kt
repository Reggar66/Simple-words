package com.ada.simplewords.data

import com.ada.simplewords.domain.models.QuizModel

data class Quiz(
    val id: String,
    val name: String,
    val wordsNumber: Int,
    val completedWords: Int
) {
    val isComplete get() = completedWords == wordsNumber

    companion object {
        fun empty() = Quiz(id = "", name = "", wordsNumber = 0, completedWords = 0)

        fun mockQuizzes() = listOf(
            QuizModel.mockAnimals.toQuizItemOrEmpty(),
            QuizModel.mockFood.toQuizItemOrEmpty(),
            QuizModel.mockSeasons.toQuizItemOrEmpty(),
        )
    }
}

/**
 * Converts [QuizModel] to [Quiz] or returns null if any field is null.
 */
fun QuizModel.toQuizItemOrNull(): Quiz? {
    return Quiz(
        id = id ?: return null,
        name = name ?: return null,
        wordsNumber = wordsNumber ?: return null,
        completedWords = completedWords ?: return null
    )
}

/**
 * Converts [QuizModel] to [Quiz] or returns [Quiz.empty] if any field is null.
 */
fun QuizModel.toQuizItemOrEmpty(): Quiz {
    return Quiz(
        id = id ?: return Quiz.empty(),
        name = name ?: return Quiz.empty(),
        wordsNumber = wordsNumber ?: return Quiz.empty(),
        completedWords = completedWords ?: return Quiz.empty()
    )
}