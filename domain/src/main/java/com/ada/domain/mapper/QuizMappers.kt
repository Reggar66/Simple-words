package com.ada.domain.mapper

import com.ada.domain.model.Quiz
import com.ada.data.model.QuizModel

/**
 * Converts [QuizModel] to [Quiz] or returns null if any field is null.
 */
fun QuizModel.toQuizOrNull(): Quiz? {
    return Quiz(
        id = id ?: return null,
        name = name ?: return null,
        wordsNumber = wordsNumber ?: return null,
        completedWords = completedWords ?: return null,
        mode = mode ?: return null
    )
}

/**
 * Converts [QuizModel] to [Quiz] or returns [Quiz.empty] if any field is null.
 */
fun QuizModel.toQuizOrEmpty(): Quiz {
    return Quiz(
        id = id ?: return Quiz.empty(),
        name = name ?: return Quiz.empty(),
        wordsNumber = wordsNumber ?: return Quiz.empty(),
        completedWords = completedWords ?: return Quiz.empty(),
        mode = mode ?: return Quiz.empty()
    )
}

fun Quiz.toQuizModel() = QuizModel(
    id = id,
    name = name,
    wordsNumber = wordsNumber,
    completedWords = completedWords,
    mode = mode
)