package com.ada.domain.usecases

import com.ada.domain.model.User

interface UpdateUserUseCase {
    operator fun invoke(user: User)
}