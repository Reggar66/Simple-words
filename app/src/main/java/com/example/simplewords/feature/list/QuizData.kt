package com.example.simplewords.feature.list

import com.example.simplewords.data.QuizItemData
import com.example.simplewords.data.WordTranslation

data class QuizData(
    val quizItem: QuizItemData,
    val words: List<WordTranslation>,
    val learnedWords: List<WordTranslation>
) {
    companion object {
        val mock by lazy {
            listOf(
                QuizData(
                    quizItem = QuizItemData.mockFood,
                    words = WordTranslation.mockFood,
                    learnedWords = WordTranslation.mockFood.filter { it.isLearned }),
                QuizData(
                    quizItem = QuizItemData.mockAnimals,
                    words = WordTranslation.mockAnimals,
                    learnedWords = WordTranslation.mockAnimals.filter { it.isLearned }),
                QuizData(
                    quizItem = QuizItemData.mockAnimalsCompleted,
                    words = WordTranslation.mockAnimalsCompleted,
                    learnedWords = WordTranslation.mockAnimalsCompleted.filter { it.isLearned }),
                QuizData(
                    quizItem = QuizItemData.mockSeasons,
                    words = WordTranslation.mockSeasons,
                    learnedWords = WordTranslation.mockSeasons.filter { it.isLearned }),
            )
        }
    }
}