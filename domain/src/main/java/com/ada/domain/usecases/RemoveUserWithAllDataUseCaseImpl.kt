package com.ada.domain.usecases

import com.ada.data.repositories.AuthenticationRepository
import com.ada.data.repositories.RealTimeDatabaseRepository
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class RemoveUserWithAllDataUseCaseImpl @Inject constructor(
    private val realTimeDatabaseRepository: RealTimeDatabaseRepository,
    private val authenticationRepository: AuthenticationRepository
) : RemoveUserWithAllDataUseCase {
    override suspend fun invoke(): Boolean = suspendCoroutine { continuation ->
        realTimeDatabaseRepository.removeAllData(
            onSuccess = {
                authenticationRepository.removeUser(
                    onSuccess = { continuation.resume(true) },
                    onFailure = { continuation.resume(false) }
                )
            },
            onFailure = { continuation.resume(false) }
        )
    }
}