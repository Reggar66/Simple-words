package com.example.simplewords.data

data class WordTranslation(
    val id: Int,
    val quizItemId: Int,
    val word: String,
    val translation: String
) {
    companion object {
        val mockAnimals by lazy {
            listOf(
                WordTranslation(id = 0, quizItemId = 0, word = "pies", translation = "dog"),
                WordTranslation(id = 1, quizItemId = 0, word = "kot", translation = "cat"),
                WordTranslation(id = 2, quizItemId = 0, word = "kaczka", translation = "duck"),
                WordTranslation(id = 3, quizItemId = 0, word = "krowa", translation = "cow")
            )
        }

        val mockFood by lazy {
            listOf(
                WordTranslation(id = 4, quizItemId = 1, word = "zupa", translation = "soup"),
                WordTranslation(id = 5, quizItemId = 1, word = "ryż", translation = "rice"),
                WordTranslation(id = 6, quizItemId = 1, word = "ser", translation = "cheese"),
                WordTranslation(id = 7, quizItemId = 1, word = "chleb", translation = "bread"),
            )
        }

        val mockSeasons by lazy {
            listOf(
                WordTranslation(id = 8, quizItemId = 2, word = "wiosna", translation = "spring"),
                WordTranslation(id = 9, quizItemId = 2, word = "lato", translation = "summer"),
                WordTranslation(id = 10, quizItemId = 2, word = "jesień", translation = "autumn"),
                WordTranslation(id = 11, quizItemId = 2, word = "zima", translation = "winter"),
            )
        }
    }
}
