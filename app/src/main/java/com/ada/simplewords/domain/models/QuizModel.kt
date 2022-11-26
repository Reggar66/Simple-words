package com.ada.simplewords.domain.models

data class QuizModel(
    val id: String? = null,
    val name: String? = null,
    val wordsNumber: Int? = null,
    val completedWords: Int? = null
) {
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
