package com.ada.changepassword

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ada.domain.model.Credentials
import com.ada.domain.usecases.ReAuthenticateUserUseCase
import com.ada.domain.usecases.UpdatePasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val reAuthenticateUserUseCase: ReAuthenticateUserUseCase,
    private val updatePasswordUseCase: UpdatePasswordUseCase
) : ViewModel() {

    private val _isAuthenticated = MutableStateFlow(false)
    private val _isUpdated = MutableStateFlow(false)

    val isAuthenticated get() = _isAuthenticated.asStateFlow()
    val isUpdated get() = _isUpdated.asStateFlow()

    fun reAuthenticate(credentials: Credentials) = viewModelScope.launch(Dispatchers.Default) {
        _isAuthenticated.update {
            reAuthenticateUserUseCase.invoke(credentials)
        }
    }


    fun updatePassword(password: String) = viewModelScope.launch(Dispatchers.Default) {
        _isUpdated.update {
            updatePasswordUseCase.invoke(password)
        }
    }
}