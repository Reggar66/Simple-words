package com.ada.domain.usecases

import com.ada.data.repositories.AuthenticationRepository
import javax.inject.Inject

class SignOutUseCaseImpl @Inject constructor(private val authenticationRepository: AuthenticationRepository) :
    SignOutUseCase {
    override fun invoke() {
        authenticationRepository.signOut()
    }
}