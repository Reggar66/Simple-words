package com.ada.domain.usecases

import com.ada.data.WordTranslation
import kotlinx.coroutines.flow.Flow

interface GetWordsUseCase {
    operator fun invoke(quizId: String): Flow<List<WordTranslation>>
}