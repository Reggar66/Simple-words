package com.ada.domain.usecases

import com.ada.data.Quiz
import com.ada.data.WordTranslation

interface CreateQuizUseCase {
    operator fun invoke(quiz: Quiz, words: List<WordTranslation>)
}