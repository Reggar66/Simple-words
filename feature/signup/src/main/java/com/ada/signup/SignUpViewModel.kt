package com.ada.signup

import androidx.lifecycle.ViewModel
import com.ada.common.debugLog
import com.ada.domain.model.Credentials
import com.ada.domain.usecases.ConvertToPermanentUseCase
import com.ada.domain.usecases.ObserveCurrentUserUseCase
import com.ada.domain.usecases.SignUpEmailAndPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpEmailAndPasswordUseCase: SignUpEmailAndPasswordUseCase,
    private val convertToPermanentUseCase: ConvertToPermanentUseCase,
    observeCurrentUserUseCase: ObserveCurrentUserUseCase
) : ViewModel() {

    val user = observeCurrentUserUseCase.invoke()

    fun signUp(credentials: Credentials, onSuccess: () -> Unit) {
        signUpEmailAndPasswordUseCase.invoke(
            credentials = credentials,
            onSuccess = {
                debugLog { "signed up user: $it" }
                onSuccess()
            }
        )
    }

    fun convertToPermanent(credentials: Credentials, onSuccess: () -> Unit) {
        convertToPermanentUseCase.invoke(
            credentials = credentials,
            onSuccess = {
                debugLog { "converted user: $it" }
                onSuccess()
            }
        )

    }
}