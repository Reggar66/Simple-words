package com.ada.simplewords.data

import com.ada.simplewords.domain.models.QuizItemModel

data class QuizItem(
    val id: String,
    val name: String,
    val wordsNumber: Int,
    val completedWords: Int
) {
    val isComplete get() = completedWords == wordsNumber

    companion object {
        fun empty() = QuizItem(id = "", name = "", wordsNumber = 0, completedWords = 0)
    }
}

/**
 * Converts [QuizItemModel] to [QuizItem] or returns null if any field is null.
 */
fun QuizItemModel.toQuizItemOrNull(): QuizItem? {
    return QuizItem(
        id = id ?: return null,
        name = name ?: return null,
        wordsNumber = wordsNumber ?: return null,
        completedWords = completedWords ?: return null
    )
}

/**
 * Converts [QuizItemModel] to [QuizItem] or returns [QuizItem.empty] if any field is null.
 */
fun QuizItemModel.toQuizItemOrEmpty(): QuizItem {
    return QuizItem(
        id = id ?: return QuizItem.empty(),
        name = name ?: return QuizItem.empty(),
        wordsNumber = wordsNumber ?: return QuizItem.empty(),
        completedWords = completedWords ?: return QuizItem.empty()
    )
}