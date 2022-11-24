package com.ada.simplewords.domain.models

data class QuizItemModel(val id: String? = null, val name: String? = null) {
    companion object {
        val mockAnimals by lazy {
            QuizItemModel(id = "0", name = "Animals")
        }

        val mockFood by lazy {
            QuizItemModel(id = "1", name = "Food")
        }

        val mockSeasons by lazy {
            QuizItemModel(id = "2", name = "Seasons")
        }

        val mockAnimalsCompleted by lazy {
            QuizItemModel(id = "3", name = "Animals Completed")
        }
    }
}
