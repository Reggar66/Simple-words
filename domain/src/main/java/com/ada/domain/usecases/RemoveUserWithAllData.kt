package com.ada.domain.usecases

interface RemoveUserWithAllData {
    suspend operator fun invoke(): Boolean
}