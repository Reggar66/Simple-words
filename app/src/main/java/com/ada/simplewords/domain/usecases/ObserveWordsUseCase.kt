package com.ada.simplewords.domain.usecases

import kotlinx.coroutines.flow.Flow

/**
 * Fetches words for given quiz id and sends them one by one in a stream.
 */
interface ObserveWordsUseCase {
    operator fun invoke(quizId: String): Flow<WordResult>
}