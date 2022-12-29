package com.ada.domain.usecases

import com.ada.domain.model.Quiz
import com.ada.domain.model.WordTranslation

interface CreateQuizUseCase {
    operator fun invoke(quiz: Quiz, words: List<WordTranslation>)
}