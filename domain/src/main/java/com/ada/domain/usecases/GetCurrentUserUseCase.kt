package com.ada.domain.usecases

import com.ada.data.model.UserModel

interface GetCurrentUserUseCase {
    operator fun invoke(): UserModel?
}