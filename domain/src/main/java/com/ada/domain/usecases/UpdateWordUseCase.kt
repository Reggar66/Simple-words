package com.ada.domain.usecases

import com.ada.data.WordTranslation

interface UpdateWordUseCase {
    operator fun invoke(wordTranslation: WordTranslation)
}