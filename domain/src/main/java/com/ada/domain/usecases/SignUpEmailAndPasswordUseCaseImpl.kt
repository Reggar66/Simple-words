package com.ada.domain.usecases

import com.ada.common.UserNameGenerator
import com.ada.data.model.UserAccountType
import com.ada.data.model.UserIdModel
import com.ada.data.model.UserModel
import com.ada.data.repositories.AuthenticationRepository
import com.ada.data.repositories.RealTimeDatabaseRepository
import com.ada.domain.mapper.toUserIdOrNull
import com.ada.domain.mapper.toUserOrNull
import com.ada.domain.model.Credentials
import com.ada.domain.model.User
import com.ada.domain.model.UserId
import javax.inject.Inject

class SignUpEmailAndPasswordUseCaseImpl @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val realTimeDatabaseRepository: RealTimeDatabaseRepository
) :
    SignUpEmailAndPasswordUseCase {
    override fun invoke(credentials: Credentials, onSuccess: (user: UserId?) -> Unit) {

        authenticationRepository.createUserWithEmail(
            email = credentials.email,
            password = credentials.password
        ) { fireBaseUser ->
            fireBaseUser?.let {
                val userIdModel = UserIdModel(
                    uid = fireBaseUser.uid,
                    userAccountType = UserAccountType.Permanent
                )
                val generatedName = UserNameGenerator.randomNameWithIcon()
                realTimeDatabaseRepository.saveUser(
                    userModel = UserModel(
                        id = userIdModel.uid,
                        name = generatedName.name(),
                        picture = null,
                        accountType = UserAccountType.Permanent,
                        emojiIcon = generatedName.emoji
                    ),
                    onSuccess = {
                        onSuccess(userIdModel.toUserIdOrNull())
                    }
                )
            }
        }
    }
}