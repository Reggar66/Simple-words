package com.ada.domain.usecases

import com.ada.data.repositories.AuthenticationRepository
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class UpdatePasswordUseCaseImpl @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) : UpdatePasswordUseCase {
    override suspend fun invoke(password: String): Boolean = suspendCoroutine { continuation ->
        authenticationRepository.updatePassword(
            password = password,
            onSuccess = { continuation.resume(true) },
            onFailure = { continuation.resume(false) }
        )
    }
}