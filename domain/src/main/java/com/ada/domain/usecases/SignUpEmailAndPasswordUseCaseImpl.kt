package com.ada.domain.usecases

import com.ada.data.model.UserAccountType
import com.ada.data.model.UserIdModel
import com.ada.data.model.UserModel
import com.ada.data.repositories.AuthenticationRepository
import com.ada.domain.mapper.toUserIdOrNull
import com.ada.domain.mapper.toUserOrNull
import com.ada.domain.model.Credentials
import com.ada.domain.model.User
import com.ada.domain.model.UserId
import javax.inject.Inject

class SignUpEmailAndPasswordUseCaseImpl @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) :
    SignUpEmailAndPasswordUseCase {
    override fun invoke(credentials: Credentials, onSuccess: (user: UserId?) -> Unit) {

        authenticationRepository.createUserWithEmail(
            email = credentials.email,
            password = credentials.password
        ) { fireBaseUser ->
            val userIdModel = UserIdModel(
                uid = fireBaseUser?.uid,
                userAccountType = UserAccountType.Permanent
            )
            onSuccess(userIdModel.toUserIdOrNull())

        }
    }
}