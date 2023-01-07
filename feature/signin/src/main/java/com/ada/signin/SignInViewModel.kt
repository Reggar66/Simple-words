package com.ada.signin

import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.ada.common.debugLog
import com.ada.data.repositories.AuthenticationRepository
import com.ada.domain.model.Credentials
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
) : ViewModel() {

    fun register(credentials: Credentials) {
        debugLog { credentials }
        authenticationRepository.createUserWithEmail(
            email = credentials.email,
            password = credentials.password,
            onComplete = {})
    }

    fun login(credentials: Credentials, onSuccess: () -> Unit, onFailure: () -> Unit) {
        debugLog { credentials }
        authenticationRepository.signInWithEmail(
            email = credentials.email,
            password = credentials.password,
            onSuccess = {
                onSuccess()
            },
            onFailure = onFailure
        )
    }

    fun resetPassword(email: String, onSuccess: () -> Unit, onFailure: () -> Unit = {}) {
        authenticationRepository.sendResetPasswordEmail(
            email = email,
            onSuccess = onSuccess,
            onFailure = onFailure
        )
    }
}