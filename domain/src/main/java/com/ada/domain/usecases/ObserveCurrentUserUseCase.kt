package com.ada.domain.usecases

import com.ada.domain.model.User
import kotlinx.coroutines.flow.Flow

interface ObserveCurrentUserUseCase {
    operator fun invoke(): Flow<User?>
}