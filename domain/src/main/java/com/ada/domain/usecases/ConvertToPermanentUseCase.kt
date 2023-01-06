package com.ada.domain.usecases

import com.ada.domain.model.Credentials
import com.ada.domain.model.User
import com.ada.domain.model.UserId

interface ConvertToPermanentUseCase {
    operator fun invoke(credentials: Credentials, onSuccess: (user: UserId?) -> Unit)
}