package com.ada.simplewords.domain.usecases

import com.ada.simplewords.data.Quiz

interface UpdateQuizUseCase {
    operator fun invoke(quiz: Quiz)
}