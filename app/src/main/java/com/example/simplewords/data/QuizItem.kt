package com.example.simplewords.data

data class QuizItem(val id: Int, val name: String) {
    companion object {
        val mockAnimals by lazy {
            QuizItem(id = 0, name = "Animals")
        }

        val mockFood by lazy {
            QuizItem(id = 1, name = "Food")
        }

        val mockSeasons by lazy {
            QuizItem(id = 2, name = "Seasons")
        }
    }
}
