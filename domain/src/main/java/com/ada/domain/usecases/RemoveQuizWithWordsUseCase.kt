package com.ada.domain.usecases

import com.ada.domain.model.Quiz

interface RemoveQuizWithWordsUseCase {
    operator fun invoke(quiz: Quiz)
}