package com.ada.simplewords.domain.usecases

import com.ada.simplewords.data.Quiz
import com.ada.simplewords.data.WordTranslation

interface CreateQuizUseCase {
    operator fun invoke(quiz: Quiz,  words: List<WordTranslation>)
}