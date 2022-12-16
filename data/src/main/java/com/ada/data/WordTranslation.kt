package com.ada.data

import com.ada.simplewords.common.Key
import com.ada.simplewords.domain.models.WordTranslationModel

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

fun WordTranslationModel.toWordTranslationOrNull(): WordTranslation? {
    return WordTranslation(
        id = id ?: return null,
        quizItemId = quizItemId ?: return null,
        word = word ?: return null,
        translation = translation ?: return null,
        isLearned = learned ?: return null,
        repeat = repeat ?: return null
    )
}

fun WordTranslationModel.toWordTranslationOrEmpty(): WordTranslation {
    return WordTranslation(
        id = id ?: return com.ada.data.WordTranslation.empty(),
        quizItemId = quizItemId ?: return com.ada.data.WordTranslation.empty(),
        word = word ?: return com.ada.data.WordTranslation.empty(),
        translation = translation ?: return com.ada.data.WordTranslation.empty(),
        isLearned = learned ?: return com.ada.data.WordTranslation.empty(),
        repeat = repeat ?: return com.ada.data.WordTranslation.empty()
    )
}
