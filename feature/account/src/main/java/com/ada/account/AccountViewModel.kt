package com.ada.account

import androidx.lifecycle.ViewModel
import com.ada.domain.usecases.ObserveCurrentUserUseCase
import com.ada.domain.usecases.SignOutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val observeCurrentUserUseCase: ObserveCurrentUserUseCase,
    private val signOutUseCase: SignOutUseCase
) :
    ViewModel() {
    val user = observeCurrentUserUseCase.invoke()

    fun signOut() {
        signOutUseCase.invoke()
    }
}