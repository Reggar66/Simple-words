package com.ada.simplewords.data

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
            repeat = 0
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
        id = id ?: return WordTranslation.empty(),
        quizItemId = quizItemId ?: return WordTranslation.empty(),
        word = word ?: return WordTranslation.empty(),
        translation = translation ?: return WordTranslation.empty(),
        isLearned = learned ?: return WordTranslation.empty(),
        repeat = repeat ?: return WordTranslation.empty()
    )
}
