package com.ada.domain.model

import com.ada.common.Key

data class WordTranslation(
    val id: Key,
    val quizItemId: Key,
    val word: String,
    val translation: String,
    val isLearned: Boolean = false,
    val repeat: Int = 3
) {
    companion object {
        fun empty() = WordTranslation(
            id = "",
            quizItemId = "",
            word = "",
            translation = "",
            isLearned = false,
            repeat = 3
        )
    }
}
