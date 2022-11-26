package com.ada.simplewords.data

import com.ada.simplewords.domain.models.QuizItemModel
import com.ada.simplewords.domain.models.WordTranslationModel

data class QuizData(
    val quiz: Quiz,
    val words: List<WordTranslationModel>,
    val learnedWords: List<WordTranslationModel>
) {

    fun isComplete() = words.size == learnedWords.size

    companion object {
        val mock by lazy {
            listOf(
                QuizData(
                    quiz = QuizItemModel.mockFood.toQuizItemOrEmpty(),
                    words = WordTranslationModel.mockFoodCompleted,
                    learnedWords = WordTranslationModel.mockFoodCompleted.filter { it.isLearned }),
                QuizData(
                    quiz = QuizItemModel.mockAnimals.toQuizItemOrEmpty(),
                    words = WordTranslationModel.mockAnimals,
                    learnedWords = WordTranslationModel.mockAnimals.filter { it.isLearned }),
                QuizData(
                    quiz = QuizItemModel.mockAnimalsCompleted.toQuizItemOrEmpty(),
                    words = WordTranslationModel.mockAnimalsCompleted,
                    learnedWords = WordTranslationModel.mockAnimalsCompleted.filter { it.isLearned }),
                QuizData(
                    quiz = QuizItemModel.mockSeasons.toQuizItemOrEmpty(),
                    words = WordTranslationModel.mockSeasons,
                    learnedWords = WordTranslationModel.mockSeasons.filter { it.isLearned }),
            )
        }
    }
}