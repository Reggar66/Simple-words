package com.ada.domain.usecases

interface UpdatePasswordUseCase {
    suspend operator fun invoke(password: String): Boolean
}