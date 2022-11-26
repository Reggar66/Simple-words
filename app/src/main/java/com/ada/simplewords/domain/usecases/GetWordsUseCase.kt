package com.ada.simplewords.domain.usecases

import com.ada.simplewords.data.WordTranslation
import kotlinx.coroutines.flow.Flow

interface GetWordsUseCase {
    operator fun invoke(quizId: String): Flow<List<WordTranslation>>
}