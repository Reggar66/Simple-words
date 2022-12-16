package com.ada.domain.usecases

import com.ada.common.Key
import com.ada.data.Quiz
import kotlinx.coroutines.flow.Flow

interface ObserveQuizUseCase {
    operator fun invoke(quizId: Key): Flow<Quiz>
}