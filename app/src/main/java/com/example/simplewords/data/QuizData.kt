package com.example.simplewords.data

import com.example.simplewords.domain.models.QuizItemData
import com.example.simplewords.domain.models.WordTranslation

data class QuizData(
    val quizItem: QuizItemData,
    val words: List<WordTranslation>,
    val learnedWords: List<WordTranslation>
) {

    fun isComplete() = words.size == learnedWords.size

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