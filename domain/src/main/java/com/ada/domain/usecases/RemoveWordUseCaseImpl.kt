package com.ada.domain.usecases

import com.ada.common.Key
import com.ada.data.repositories.RealTimeDatabaseRepository
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class RemoveWordUseCaseImpl @Inject constructor(
    private val realTimeDatabaseRepository: RealTimeDatabaseRepository
) : RemoveWordUseCase {
    override suspend fun invoke(quizId: Key, wordId: Key): Boolean =
        suspendCoroutine { continuation ->
            realTimeDatabaseRepository.removeWord(
                quizId = quizId,
                wordId = wordId,
                onSuccess = { continuation.resume(true) },
                onFailure = { continuation.resume(false) }
            )
        }
}