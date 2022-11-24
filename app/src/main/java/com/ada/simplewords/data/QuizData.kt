package com.ada.simplewords.data

import com.ada.simplewords.domain.models.QuizItemModel
import com.ada.simplewords.domain.models.WordTranslation

data class QuizData(
    val quizItemModel: QuizItemModel,
    val words: List<WordTranslation>,
    val learnedWords: List<WordTranslation>
) {

    fun isComplete() = words.size == learnedWords.size

    companion object {
        val mock by lazy {
            listOf(
                QuizData(
                    quizItemModel = QuizItemModel.mockFood,
                    words = WordTranslation.mockFood,
                    learnedWords = WordTranslation.mockFood.filter { it.isLearned }),
                QuizData(
                    quizItemModel = QuizItemModel.mockAnimals,
                    words = WordTranslation.mockAnimals,
                    learnedWords = WordTranslation.mockAnimals.filter { it.isLearned }),
                QuizData(
                    quizItemModel = QuizItemModel.mockAnimalsCompleted,
                    words = WordTranslation.mockAnimalsCompleted,
                    learnedWords = WordTranslation.mockAnimalsCompleted.filter { it.isLearned }),
                QuizData(
                    quizItemModel = QuizItemModel.mockSeasons,
                    words = WordTranslation.mockSeasons,
                    learnedWords = WordTranslation.mockSeasons.filter { it.isLearned }),
            )
        }
    }
}