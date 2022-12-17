package com.ada.domain.usecases

import com.ada.domain.model.WordTranslation
import kotlinx.coroutines.flow.Flow

interface GetWordsUseCase {
    operator fun invoke(quizId: String): Flow<List<WordTranslation>>
}