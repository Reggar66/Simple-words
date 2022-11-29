package com.ada.simplewords.domain.usecases

import com.ada.simplewords.data.WordTranslation

interface UpdateWordUseCase {
    operator fun invoke(wordTranslation: WordTranslation)
}