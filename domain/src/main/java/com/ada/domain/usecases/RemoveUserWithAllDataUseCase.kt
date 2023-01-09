package com.ada.domain.usecases

interface RemoveUserWithAllDataUseCase {
    suspend operator fun invoke(): Boolean
}