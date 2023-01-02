package com.ada.domain.usecases

import com.ada.common.UserNameGenerator
import com.ada.data.model.UserModel
import com.ada.data.repositories.AuthenticationRepository
import com.ada.data.repositories.RealTimeDatabaseRepository
import javax.inject.Inject

class SignInAnonymousUserUseCaseImpl @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val realTimeDatabaseRepository: RealTimeDatabaseRepository
) : SignInAnonymousUserUseCase {
    override fun invoke(onSuccess: () -> Unit) {
        if (!authenticationRepository.isSignedIn())
            authenticationRepository.signInAnonymously {
                it?.let {
                    realTimeDatabaseRepository.saveUser(
                        UserModel(
                            id = it.uid,
                            name = UserNameGenerator.randomName(),
                            picture = null // TODO: picture?
                        ),
                        onSuccess = onSuccess
                    )
                }
            }
    }
}