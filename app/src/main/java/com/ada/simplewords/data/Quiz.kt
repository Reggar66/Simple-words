package com.ada.simplewords.data

import com.ada.simplewords.data.mapper.toQuizItemOrEmpty
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