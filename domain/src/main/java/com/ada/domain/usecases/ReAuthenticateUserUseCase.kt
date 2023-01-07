package com.ada.domain.usecases

import com.ada.domain.model.Credentials

interface ReAuthenticateUserUseCase {
    suspend operator fun invoke(credentials: Credentials): Boolean
}