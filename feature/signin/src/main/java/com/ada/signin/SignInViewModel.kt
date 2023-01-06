package com.ada.signin

import androidx.lifecycle.ViewModel
import com.ada.common.debugLog
import com.ada.data.repositories.AuthenticationRepository
import com.ada.domain.model.Credentials
import com.ada.domain.usecases.SignInAnonymousUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val signInAnonymousUserUseCase: SignInAnonymousUserUseCase
) :
    ViewModel() {


    fun signInAnon(onComplete: () -> Unit) {
        signInAnonymousUserUseCase.invoke(onSuccess = {
            onComplete()
        })
    }

    fun signOut() = authenticationRepository.signOut()

    fun register(credentials: Credentials) {
        debugLog { credentials }
        authenticationRepository.createUserWithEmail(
            email = credentials.email,
            password = credentials.password,
            onComplete = {})
    }

    fun login(credentials: Credentials, onComplete: () -> Unit) {
        debugLog { credentials }
        authenticationRepository.signInWithEmail(
            email = credentials.email,
            password = credentials.password,
            onComplete = {
                onComplete()
            }
        )
    }
    }