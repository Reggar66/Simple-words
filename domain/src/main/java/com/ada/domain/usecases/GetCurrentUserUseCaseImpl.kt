package com.ada.domain.usecases

import com.ada.common.debugLog
import com.ada.data.model.UserModel
import com.ada.data.repositories.AuthenticationRepository
import javax.inject.Inject

class GetCurrentUserUseCaseImpl @Inject constructor(private val authenticationRepository: AuthenticationRepository) :
    GetCurrentUserUseCase {
    override fun invoke(): UserModel? {
        val firebaseUser = authenticationRepository.getCurrentUser()

        return UserModel(
            id = firebaseUser?.uid ?: return null,
            name = firebaseUser.displayName,
            picture = null
        ).also { debugLog { "Current user model: $it" } }
    }
}