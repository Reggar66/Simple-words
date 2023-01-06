package com.ada.welcome

import androidx.lifecycle.ViewModel
import com.ada.data.repositories.AuthenticationRepository
import com.ada.domain.usecases.SignInAnonymousUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val signInAnonymousUserUseCase: SignInAnonymousUserUseCase
) : ViewModel() {

    fun signInAnon(onComplete: () -> Unit) {
        signInAnonymousUserUseCase.invoke(onSuccess = {
            onComplete()
        })
    }

    fun isSignedIn() = authenticationRepository.isSignedIn()
}