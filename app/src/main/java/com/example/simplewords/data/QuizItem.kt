package com.example.simplewords.data

data class QuizItem(val id: Int, val name: String) {
    companion object {
        val mockAnimals by lazy {
            QuizItem(id = 0, name = "Animals")
        }
    }
}
