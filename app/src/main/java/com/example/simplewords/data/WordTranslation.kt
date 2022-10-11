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
    }
}
