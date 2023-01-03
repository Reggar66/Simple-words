package com.ada.domain.usecases

import com.ada.common.UserNameGenerator
import com.ada.data.model.UserAccountType
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
                    val generatedName = UserNameGenerator.randomNameWithIcon()
                    realTimeDatabaseRepository.saveUser(
                        UserModel(
                            id = it.uid,
                            name = generatedName.name(),
                            picture = null,
                            accountType = UserAccountType.Anonymous,
                            emojiIcon = generatedName.emoji
                        ),
                        onSuccess = onSuccess
                    )
                }
            }
    }
}