package com.ada.domain.mapper

import com.ada.domain.model.WordTranslation
import com.ada.data.model.WordTranslationModel

fun WordTranslation.toWordTranslationModel() = WordTranslationModel(
    id = id,
    quizItemId = quizItemId,
    word = word,
    translation = translation,
    learned = isLearned,
    repeat = repeat
)

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