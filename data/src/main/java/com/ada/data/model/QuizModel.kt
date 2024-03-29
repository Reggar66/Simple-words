package com.ada.data.model

import com.ada.common.Key

data class QuizModel(
    val id: Key? = null,
    val name: String? = null,
    val wordsNumber: Int? = null,
    val completedWords: Int? = null,
    val mode: QuizMode? = null
) {

    fun toMap() = mapOf<Key, Any?>(
        "id" to id,
        "name" to name,
        "wordsNumber" to wordsNumber,
        "completedWords" to completedWords,
        "mode" to mode
    )

    companion object {
        val mockAnimals by lazy {
            QuizModel(
                id = "0",
                name = "Animals",
                wordsNumber = WordTranslationModel.mockAnimals.size,
                completedWords = WordTranslationModel.mockAnimals.count { it.learned == true },
                mode = QuizMode.Classic
            )
        }

        val mockFood by lazy {
            QuizModel(
                id = "1",
                name = "Food",
                wordsNumber = WordTranslationModel.mockFoodCompleted.size,
                completedWords = WordTranslationModel.mockFoodCompleted.count { it.learned == true },
                mode = QuizMode.Modern
            )
        }

        val mockSeasons by lazy {
            QuizModel(
                id = "2",
                name = "Seasons",
                wordsNumber = WordTranslationModel.mockSeasons.size,
                completedWords = WordTranslationModel.mockSeasons.count { it.learned == true },
                mode = QuizMode.Classic
            )
        }

        val mockAnimalsCompleted by lazy {
            QuizModel(
                id = "3",
                name = "Animals Completed",
                wordsNumber = WordTranslationModel.mockAnimalsCompleted.size,
                completedWords = WordTranslationModel.mockAnimalsCompleted.count { it.learned == true },
                mode = QuizMode.Classic
            )
        }
    }
}

enum class QuizMode(val code: Int) {
    Classic(0),
    Modern(1)
}