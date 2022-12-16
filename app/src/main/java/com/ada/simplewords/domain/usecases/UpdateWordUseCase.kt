package com.ada.simplewords.domain.usecases

import com.ada.data.WordTranslation

interface UpdateWordUseCase {
    operator fun invoke(wordTranslation: com.ada.data.WordTranslation)
}