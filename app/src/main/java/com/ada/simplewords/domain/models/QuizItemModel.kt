package com.ada.simplewords.domain.models

data class QuizItemModel(
    val id: String? = null,
    val name: String? = null,
    val wordsNumber: Int? = null,
    val completedWords: Int? = null
) {
    companion object {
        val mockAnimals by lazy {
            QuizItemModel(
                id = "0",
                name = "Animals",
                wordsNumber = WordTranslationModel.mockAnimals.size,
                completedWords = WordTranslationModel.mockAnimals.count { it.isLearned }
            )
        }

        val mockFood by lazy {
            QuizItemModel(
                id = "1",
                name = "Food",
                wordsNumber = WordTranslationModel.mockFoodCompleted.size,
                completedWords = WordTranslationModel.mockFoodCompleted.count { it.isLearned })
        }

        val mockSeasons by lazy {
            QuizItemModel(
                id = "2",
                name = "Seasons",
                wordsNumber = WordTranslationModel.mockSeasons.size,
                completedWords = WordTranslationModel.mockSeasons.count { it.isLearned })
        }

        val mockAnimalsCompleted by lazy {
            QuizItemModel(id = "3",
                name = "Animals Completed",
                wordsNumber = WordTranslationModel.mockAnimalsCompleted.size,
                completedWords = WordTranslationModel.mockAnimalsCompleted.count { it.isLearned }
            )
        }
    }
}
