package com.ada.simplewords.data

import com.ada.simplewords.domain.models.QuizItemModel
import com.ada.simplewords.domain.models.WordTranslationModel

data class QuizData(
    val quizItem: QuizItem,
    val words: List<WordTranslationModel>,
    val learnedWords: List<WordTranslationModel>
) {

    fun isComplete() = words.size == learnedWords.size

    companion object {
        val mock by lazy {
            listOf(
                QuizData(
                    quizItem = QuizItemModel.mockFood.toQuizItemOrEmpty(),
                    words = WordTranslationModel.mockFoodCompleted,
                    learnedWords = WordTranslationModel.mockFoodCompleted.filter { it.isLearned }),
                QuizData(
                    quizItem = QuizItemModel.mockAnimals.toQuizItemOrEmpty(),
                    words = WordTranslationModel.mockAnimals,
                    learnedWords = WordTranslationModel.mockAnimals.filter { it.isLearned }),
                QuizData(
                    quizItem = QuizItemModel.mockAnimalsCompleted.toQuizItemOrEmpty(),
                    words = WordTranslationModel.mockAnimalsCompleted,
                    learnedWords = WordTranslationModel.mockAnimalsCompleted.filter { it.isLearned }),
                QuizData(
                    quizItem = QuizItemModel.mockSeasons.toQuizItemOrEmpty(),
                    words = WordTranslationModel.mockSeasons,
                    learnedWords = WordTranslationModel.mockSeasons.filter { it.isLearned }),
            )
        }
    }
}