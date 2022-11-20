package com.ada.simplewords.domain.models

data class QuizItemData(val id: Int, val name: String) {
    companion object {
        val mockAnimals by lazy {
            QuizItemData(id = 0, name = "Animals")
        }

        val mockFood by lazy {
            QuizItemData(id = 1, name = "Food")
        }

        val mockSeasons by lazy {
            QuizItemData(id = 2, name = "Seasons")
        }

        val mockAnimalsCompleted by lazy {
            QuizItemData(id = 3, name = "Animals Completed")
        }
    }
}
