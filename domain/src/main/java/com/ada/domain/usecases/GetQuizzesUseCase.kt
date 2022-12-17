package com.ada.domain.usecases

import com.ada.domain.model.Quiz
import kotlinx.coroutines.flow.Flow

interface GetQuizzesUseCase {
    operator fun invoke(): Flow<List<Quiz>>
}