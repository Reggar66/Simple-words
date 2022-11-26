package com.ada.simplewords.domain.usecases

import com.ada.simplewords.data.Quiz
import kotlinx.coroutines.flow.Flow

interface GetQuizzesUseCase {
    operator fun invoke(): Flow<List<Quiz>>
}