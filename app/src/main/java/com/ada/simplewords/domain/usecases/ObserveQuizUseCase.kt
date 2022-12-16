package com.ada.simplewords.domain.usecases

import com.ada.simplewords.common.Key
import com.ada.data.Quiz
import kotlinx.coroutines.flow.Flow

interface ObserveQuizUseCase {
    operator fun invoke(quizId: Key): Flow<com.ada.data.Quiz>
}