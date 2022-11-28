package com.ada.simplewords.domain.usecases

import com.ada.simplewords.data.Quiz

interface CreateQuizUseCase {
    operator fun invoke(quiz: Quiz)
}