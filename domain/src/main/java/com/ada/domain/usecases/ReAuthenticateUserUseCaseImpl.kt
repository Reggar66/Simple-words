package com.ada.domain.usecases

import com.ada.data.repositories.AuthenticationRepository
import com.ada.domain.model.Credentials
import com.google.firebase.auth.EmailAuthProvider
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ReAuthenticateUserUseCaseImpl @Inject constructor(
    private var authenticationRepository: AuthenticationRepository
) : ReAuthenticateUserUseCase {
    override suspend fun invoke(credentials: Credentials): Boolean =
        suspendCoroutine { continuation ->
            val authCredentials = EmailAuthProvider
                .getCredential(credentials.email, credentials.password)

            authenticationRepository.reAuthenticate(
                credentials = authCredentials,
                onSuccess = { continuation.resume(true) },
                onFailure = { continuation.resume(false) }
            )
        }
}