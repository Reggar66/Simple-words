package com.ada.simplewords.domain.usecases

import com.ada.data.Quiz

interface UpdateQuizUseCase {
    operator fun invoke(quiz: com.ada.data.Quiz)
}