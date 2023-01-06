package com.ada.domain.usecases

import com.ada.domain.model.Credentials
import com.ada.domain.model.User
import com.ada.domain.model.UserId
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface SignUpEmailAndPasswordUseCase {
    operator fun invoke(credentials: Credentials, onSuccess: (user: UserId?) -> Unit)
}