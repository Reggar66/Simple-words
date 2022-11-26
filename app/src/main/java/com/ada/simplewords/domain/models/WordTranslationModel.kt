package com.ada.simplewords.domain.models

data class WordTranslationModel(
    val id: Int,
    val quizItemId: Int,
    val word: String,
    val translation: String,
    val isLearned: Boolean = false,
    val repeat: Int = 3
) {
    companion object {
        val mockAnimals by lazy {
            listOf(
                WordTranslationModel(
                    id = 0,
                    quizItemId = 0,
                    word = "pies",
                    translation = "dog",
                    isLearned = true
                ),
                WordTranslationModel(id = 1, quizItemId = 0, word = "kot", translation = "cat"),
                WordTranslationModel(id = 2, quizItemId = 0, word = "kaczka", translation = "duck"),
                WordTranslationModel(id = 3, quizItemId = 0, word = "krowa", translation = "cow")
            )
        }

        val mockAnimalsCompleted by lazy {
            listOf(
                WordTranslationModel(
                    id = 0,
                    quizItemId = 0,
                    word = "pies",
                    translation = "dog",
                    isLearned = true
                ),
                WordTranslationModel(
                    id = 1,
                    quizItemId = 0,
                    word = "kot",
                    translation = "cat",
                    isLearned = true
                ),
                WordTranslationModel(
                    id = 2,
                    quizItemId = 0,
                    word = "kaczka",
                    translation = "duck",
                    isLearned = true
                ),
                WordTranslationModel(
                    id = 3,
                    quizItemId = 0,
                    word = "krowa",
                    translation = "cow",
                    isLearned = true
                )
            )
        }

        val mockFoodCompleted by lazy {
            listOf(
                WordTranslationModel(
                    id = 4,
                    quizItemId = 1,
                    word = "zupa",
                    translation = "soup",
                    isLearned = true
                ),
                WordTranslationModel(
                    id = 5,
                    quizItemId = 1,
                    word = "ryż",
                    translation = "rice",
                    isLearned = true
                ),
                WordTranslationModel(
                    id = 6, quizItemId = 1,
                    word = "ser",
                    translation = "cheese",
                    isLearned = true
                ),
                WordTranslationModel(
                    id = 7,
                    quizItemId = 1,
                    word = "chleb",
                    translation = "bread",
                    isLearned = true
                ),
            )
        }

        val mockSeasons by lazy {
            listOf(
                WordTranslationModel(
                    id = 8,
                    quizItemId = 2,
                    word = "wiosna",
                    translation = "spring",
                    isLearned = true
                ),
                WordTranslationModel(
                    id = 9,
                    quizItemId = 2,
                    word = "lato",
                    translation = "summer",
                    isLearned = true
                ),
                WordTranslationModel(
                    id = 10,
                    quizItemId = 2,
                    word = "jesień",
                    translation = "autumn"
                ),
                WordTranslationModel(
                    id = 11,
                    quizItemId = 2,
                    word = "zima",
                    translation = "winter"
                ),
            )
        }
    }
}
