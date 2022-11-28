package com.ada.simplewords.data.mapper

import com.ada.simplewords.data.Quiz
import com.ada.simplewords.domain.models.QuizModel

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

fun Quiz.toQuizModel() = QuizModel(
    id = id,
    name = name,
    wordsNumber = wordsNumber,
    completedWords = completedWords
)