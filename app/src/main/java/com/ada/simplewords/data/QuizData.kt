package com.ada.simplewords.data

import com.ada.simplewords.domain.models.QuizItemModel
import com.ada.simplewords.domain.models.WordTranslationModel

data class QuizData(
    val quizItemModel: QuizItemModel,
    val words: List<WordTranslationModel>,
    val learnedWords: List<WordTranslationModel>
) {

    fun isComplete() = words.size == learnedWords.size

    companion object {
        val mock by lazy {
            listOf(
                QuizData(
                    quizItemModel = QuizItemModel.mockFood,
                    words = WordTranslationModel.mockFood,
                    learnedWords = WordTranslationModel.mockFood.filter { it.isLearned }),
                QuizData(
                    quizItemModel = QuizItemModel.mockAnimals,
                    words = WordTranslationModel.mockAnimals,
                    learnedWords = WordTranslationModel.mockAnimals.filter { it.isLearned }),
                QuizData(
                    quizItemModel = QuizItemModel.mockAnimalsCompleted,
                    words = WordTranslationModel.mockAnimalsCompleted,
                    learnedWords = WordTranslationModel.mockAnimalsCompleted.filter { it.isLearned }),
                QuizData(
                    quizItemModel = QuizItemModel.mockSeasons,
                    words = WordTranslationModel.mockSeasons,
                    learnedWords = WordTranslationModel.mockSeasons.filter { it.isLearned }),
            )
        }
    }
}