package com.ada.domain.model

import com.ada.data.model.QuizModel
import com.ada.domain.mapper.toQuizOrEmpty

data class Quiz(
    val id: String,
    val name: String,
    val wordsNumber: Int,
    val completedWords: Int
    // TODO add languages that are used i.e. Pair("Polish", "English")
) {
    val isComplete get() = completedWords == wordsNumber

    companion object {
        fun empty() = Quiz(id = "", name = "", wordsNumber = 0, completedWords = 0)

        // TODO: fix mock. Have to move it i.e. to domain
        fun mockQuizzes() = listOf(
            QuizModel.mockAnimals.toQuizOrEmpty(),
            QuizModel.mockFood.toQuizOrEmpty(),
            QuizModel.mockSeasons.toQuizOrEmpty(),
        )
    }
}