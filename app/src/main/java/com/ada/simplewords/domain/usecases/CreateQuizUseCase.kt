package com.ada.simplewords.domain.usecases

import com.ada.data.Quiz
import com.ada.data.WordTranslation

interface CreateQuizUseCase {
    operator fun invoke(quiz: com.ada.data.Quiz, words: List<com.ada.data.WordTranslation>)
}