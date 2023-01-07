package com.ada.domain.usecases

import com.ada.data.repositories.RealTimeDatabaseRepository
import com.ada.domain.mapper.toUserModel
import com.ada.domain.model.User
import javax.inject.Inject

class UpdateUserUseCaseImpl @Inject constructor(
    private val realTimeDatabaseRepository: RealTimeDatabaseRepository
) : UpdateUserUseCase {
    override fun invoke(user: User) {
        realTimeDatabaseRepository.updateUser(userModel = user.toUserModel())
    }
}