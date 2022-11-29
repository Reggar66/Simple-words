package com.ada.simplewords.data.mapper

import com.ada.simplewords.data.WordTranslation
import com.ada.simplewords.domain.models.WordTranslationModel

fun WordTranslation.toWordTranslationModel() = WordTranslationModel(
    id = id,
    quizItemId = quizItemId,
    word = word,
    translation = translation,
    learned = isLearned,
    repeat = repeat
)