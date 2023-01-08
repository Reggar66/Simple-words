package com.ada.accountdelete

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ada.domain.model.Credentials
import com.ada.domain.usecases.ReAuthenticateUserUseCase
import com.ada.domain.usecases.RemoveUserWithAllData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeleteAccountViewModel @Inject constructor(
    private val removeUserWithAllData: RemoveUserWithAllData,
    private val reAuthenticateUserUseCase: ReAuthenticateUserUseCase
) : ViewModel() {


    fun removeAccountWithData(credentials: Credentials, onSuccess: () -> Unit) =
        viewModelScope.launch {
            if (reAuthenticateUserUseCase.invoke(credentials = credentials)) {
                if (removeUserWithAllData.invoke())
                    onSuccess()
            }
        }
}