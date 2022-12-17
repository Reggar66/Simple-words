package com.ada.domain.usecases

import com.ada.common.Key
import com.ada.domain.model.Quiz
import kotlinx.coroutines.flow.Flow

interface ObserveQuizUseCase {
    operator fun invoke(quizId: Key): Flow<Quiz>
}