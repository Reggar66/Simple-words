package com.ada.domain.usecases

import com.ada.data.model.UserAccountType
import com.ada.data.model.UserIdModel
import com.ada.data.model.UserModel
import com.ada.data.repositories.AuthenticationRepository
import com.ada.data.repositories.RealTimeDatabaseRepository
import com.ada.domain.mapper.toUserIdOrNull
import com.ada.domain.model.Credentials
import com.ada.domain.model.User
import com.ada.domain.model.UserId
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.database.ktx.getValue
import javax.inject.Inject

class ConvertToPermanentUseCaseImpl @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val realTimeDatabaseRepository: RealTimeDatabaseRepository
) : ConvertToPermanentUseCase {
    override fun invoke(credentials: Credentials, onSuccess: (user: UserId?) -> Unit) {

        val authCredentials =
            EmailAuthProvider.getCredential(credentials.email, credentials.password)

        authenticationRepository.convertAnonymousToPermanentAccount(
            credential = authCredentials,
            onComplete = {

                val userIdModel = UserIdModel(
                    uid = it?.uid, userAccountType = when (it?.isAnonymous) {
                        true -> UserAccountType.Anonymous
                        false -> UserAccountType.Permanent
                        else -> null
                    }
                )

                val userId = userIdModel.toUserIdOrNull()

                if (userId != null) {
                    realTimeDatabaseRepository.userRef().get().addOnSuccessListener { snapshot ->
                        val updatedUserModel = snapshot.getValue<UserModel>()
                            ?.copy(id = userId.uid, accountType = userId.userAccountType)

                        if (updatedUserModel != null)
                            realTimeDatabaseRepository.updateUser(updatedUserModel)
                    }
                }

                onSuccess(userId)
            }
        )
    }
}