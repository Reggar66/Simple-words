package com.ada.domain.usecases

interface SignInAnonymousUserUseCase {
    operator fun invoke(onSuccess: () -> Unit)
}