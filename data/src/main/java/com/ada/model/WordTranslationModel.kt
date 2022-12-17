package com.ada.model

import com.ada.common.Key

data class WordTranslationModel(
    val id: Key? = null,
    val quizItemId: String? = null,
    val word: String? = null,
    val translation: String? = null,
    val learned: Boolean? = null,
    val repeat: Int? = null
) {

    fun toMap() = mapOf<Key, Any?>(
        "id" to id,
        "quizItemId" to quizItemId,
        "word" to word,
        "translation" to translation,
        "learned" to learned,
        "repeat" to repeat
    )

    companion object {
        val mockAnimals by lazy {
            listOf(
                WordTranslationModel(
                    id = "0",
                    quizItemId = "0",
                    word = "pies",
                    translation = "dog",
                    learned = true,
                    repeat = 3
                ),
                WordTranslationModel(
                    id = "1",
                    quizItemId = "0",
                    word = "kot",
                    translation = "cat",
                    learned = false,
                    repeat = 3
                ),
                WordTranslationModel(
                    id = "2",
                    quizItemId = "0",
                    word = "kaczka",
                    translation = "duck",
                    learned = false,
                    repeat = 3
                ),
                WordTranslationModel(
                    id = "3",
                    quizItemId = "0",
                    word = "krowa",
                    translation = "cow",
                    learned = false,
                    repeat = 3
                )
            )
        }

        val mockAnimalsCompleted by lazy {
            listOf(
                WordTranslationModel(
                    id = "0",
                    quizItemId = "0",
                    word = "pies",
                    translation = "dog",
                    learned = true,
                    repeat = 3
                ),
                WordTranslationModel(
                    id = "1",
                    quizItemId = "0",
                    word = "kot",
                    translation = "cat",
                    learned = true,
                    repeat = 3
                ),
                WordTranslationModel(
                    id = "2",
                    quizItemId = "0",
                    word = "kaczka",
                    translation = "duck",
                    learned = true,
                    repeat = 3
                ),
                WordTranslationModel(
                    id = "3",
                    quizItemId = "0",
                    word = "krowa",
                    translation = "cow",
                    learned = true,
                    repeat = 3
                )
            )
        }

        val mockFoodCompleted by lazy {
            listOf(
                WordTranslationModel(
                    id = "4",
                    quizItemId = "0",
                    word = "zupa",
                    translation = "soup",
                    learned = true,
                    repeat = 3
                ),
                WordTranslationModel(
                    id = "5",
                    quizItemId = "0",
                    word = "ryż",
                    translation = "rice",
                    learned = true,
                    repeat = 3
                ),
                WordTranslationModel(
                    id = "6", quizItemId = "0",
                    word = "ser",
                    translation = "cheese",
                    learned = true,
                    repeat = 3
                ),
                WordTranslationModel(
                    id = "7",
                    quizItemId = "0",
                    word = "chleb",
                    translation = "bread",
                    learned = true,
                    repeat = 3
                ),
            )
        }

        val mockSeasons by lazy {
            listOf(
                WordTranslationModel(
                    id = "8",
                    quizItemId = "0",
                    word = "wiosna",
                    translation = "spring",
                    learned = true,
                    repeat = 3
                ),
                WordTranslationModel(
                    id = "9",
                    quizItemId = "0",
                    word = "lato",
                    translation = "summer",
                    learned = true,
                    repeat = 3
                ),
                WordTranslationModel(
                    id = "10",
                    quizItemId = "0",
                    word = "jesień",
                    translation = "autumn",
                    learned = false,
                    repeat = 3
                ),
                WordTranslationModel(
                    id = "11",
                    quizItemId = "0",
                    word = "zima",
                    translation = "winter",
                    learned = false,
                    repeat = 3
                ),
            )
        }
    }
}
