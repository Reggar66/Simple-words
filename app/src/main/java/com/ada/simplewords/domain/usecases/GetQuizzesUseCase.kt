package com.ada.simplewords.domain.usecases

import com.ada.simplewords.data.QuizItem
import com.ada.simplewords.domain.models.QuizItemModel
import kotlinx.coroutines.flow.Flow

interface GetQuizzesUseCase {
    operator fun invoke(): Flow<List<QuizItem>>
}