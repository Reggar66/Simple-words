package com.ada.domain.usecases

import com.ada.data.Quiz

interface UpdateQuizUseCase {
    operator fun invoke(quiz: Quiz)
}