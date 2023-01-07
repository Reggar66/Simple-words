package com.ada.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ada.domain.model.User
import com.ada.domain.usecases.ObserveCurrentUserUseCase
import com.ada.domain.usecases.SignOutUseCase
import com.ada.domain.usecases.UpdateUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val observeCurrentUserUseCase: ObserveCurrentUserUseCase,
    private val signOutUseCase: SignOutUseCase,
    private val updateUserUseCase: UpdateUserUseCase
) :
    ViewModel() {
    private val _user = MutableStateFlow<User?>(null)//observeCurrentUserUseCase.invoke()

    val user get() = _user.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            observeCurrentUserUseCase.invoke().collect { newUser ->
                _user.update {
                    newUser
                }
            }
        }
    }

    fun signOut() {
        signOutUseCase.invoke()
    }

    fun updateIcon(emoji: String) {
        val user = user.value
        user?.let {
            updateUserUseCase.invoke(user = user.copy(emojiIcon = emoji))
        }
    }
}