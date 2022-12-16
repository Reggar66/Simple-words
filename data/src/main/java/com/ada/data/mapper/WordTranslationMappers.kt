package com.ada.data.mapper

import com.ada.data.WordTranslation
import com.ada.domain.models.WordTranslationModel

fun WordTranslation.toWordTranslationModel() = WordTranslationModel(
    id = id,
    quizItemId = quizItemId,
    word = word,
    translation = translation,
    learned = isLearned,
    repeat = repeat
)