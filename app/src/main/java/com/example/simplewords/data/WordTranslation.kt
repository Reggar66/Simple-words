package com.example.simplewords.data

data class WordTranslation(
    val id: Int,
    val quizItemId: Int,
    val word: String,
    val translation: String,
    val isLearned: Boolean = false
) {
    companion object {
        val mockAnimals by lazy {
            listOf(
                WordTranslation(
                    id = 0,
                    quizItemId = 0,
                    word = "pies",
                    translation = "dog",
                    isLearned = true
                ),
                WordTranslation(id = 1, quizItemId = 0, word = "kot", translation = "cat"),
                WordTranslation(id = 2, quizItemId = 0, word = "kaczka", translation = "duck"),
                WordTranslation(id = 3, quizItemId = 0, word = "krowa", translation = "cow")
            )
        }

        val mockAnimalsCompleted by lazy {
            listOf(
                WordTranslation(
                    id = 0,
                    quizItemId = 0,
                    word = "pies",
                    translation = "dog",
                    isLearned = true
                ),
                WordTranslation(
                    id = 1, quizItemId = 0, word = "kot", translation = "cat",
                    isLearned = true
                ),
                WordTranslation(
                    id = 2, quizItemId = 0, word = "kaczka", translation = "duck",
                    isLearned = true
                ),
                WordTranslation(
                    id = 3, quizItemId = 0, word = "krowa", translation = "cow",
                    isLearned = true
                )
            )
        }

        val mockFood by lazy {
            listOf(
                WordTranslation(
                    id = 4,
                    quizItemId = 1,
                    word = "zupa",
                    translation = "soup",
                    isLearned = true
                ),
                WordTranslation(
                    id = 5,
                    quizItemId = 1,
                    word = "ryż",
                    translation = "rice",
                    isLearned = true
                ),
                WordTranslation(
                    id = 6, quizItemId = 1, word = "ser", translation = "cheese",
                    isLearned = true
                ),
                WordTranslation(
                    id = 7, quizItemId = 1, word = "chleb", translation = "bread",
                    isLearned = true
                ),
            )
        }

        val mockSeasons by lazy {
            listOf(
                WordTranslation(
                    id = 8,
                    quizItemId = 2,
                    word = "wiosna",
                    translation = "spring",
                    isLearned = true
                ),
                WordTranslation(
                    id = 9,
                    quizItemId = 2,
                    word = "lato",
                    translation = "summer",
                    isLearned = true
                ),
                WordTranslation(
                    id = 10,
                    quizItemId = 2,
                    word = "jesień",
                    translation = "autumn"
                ),
                WordTranslation(
                    id = 11,
                    quizItemId = 2,
                    word = "zima",
                    translation = "winter"
                ),
            )
        }
    }
}
