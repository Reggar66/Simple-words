package com.ada.simplewords.domain.usecases

import com.ada.simplewords.data.WordTranslation
import kotlinx.coroutines.flow.Flow

/**
 * Fetches words for given quiz id and sends them one by one in a stream.
 */
interface GetWordsByOneUseCase {
    operator fun invoke(quizId: String): Flow<WordResult>
}