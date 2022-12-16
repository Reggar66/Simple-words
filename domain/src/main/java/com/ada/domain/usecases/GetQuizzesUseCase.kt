package com.ada.domain.usecases

import com.ada.data.Quiz
import kotlinx.coroutines.flow.Flow

interface GetQuizzesUseCase {
    operator fun invoke(): Flow<List<Quiz>>
}