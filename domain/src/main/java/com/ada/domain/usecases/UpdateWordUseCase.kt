package com.ada.domain.usecases

import com.ada.domain.model.WordTranslation

interface UpdateWordUseCase {
    operator fun invoke(wordTranslation: WordTranslation)
}