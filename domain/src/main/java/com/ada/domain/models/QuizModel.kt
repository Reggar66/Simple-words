package com.ada.domain.models

import com.ada.common.Key

data class QuizModel(
    val id: Key? = null,
    val name: String? = null,
    val wordsNumber: Int? = null,
    val completedWords: Int? = null
) {

    fun toMap() = mapOf<Key, Any?>(
        "id" to id,
        "name" to name,
        "wordsNumber" to wordsNumber,
        "completedWords" to completedWords
    )

    companion object {
        val mockAnimals by lazy {
            QuizModel(
                id = "0",
                name = "Animals",
                wordsNumber = WordTranslationModel.mockAnimals.size,
                completedWords = WordTranslationModel.mockAnimals.count { it.learned == true }
            )
        }

        val mockFood by lazy {
            QuizModel(
                id = "1",
                name = "Food",
                wordsNumber = WordTranslationModel.mockFoodCompleted.size,
                completedWords = WordTranslationModel.mockFoodCompleted.count { it.learned == true })
        }

        val mockSeasons by lazy {
            QuizModel(
                id = "2",
                name = "Seasons",
                wordsNumber = WordTranslationModel.mockSeasons.size,
                completedWords = WordTranslationModel.mockSeasons.count { it.learned == true })
        }

        val mockAnimalsCompleted by lazy {
            QuizModel(id = "3",
                name = "Animals Completed",
                wordsNumber = WordTranslationModel.mockAnimalsCompleted.size,
                completedWords = WordTranslationModel.mockAnimalsCompleted.count { it.learned == true }
            )
        }
    }
}
