package com.ada.domain.usecases

import com.ada.domain.model.Quiz

interface UpdateQuizUseCase {
    operator fun invoke(quiz: Quiz)
}