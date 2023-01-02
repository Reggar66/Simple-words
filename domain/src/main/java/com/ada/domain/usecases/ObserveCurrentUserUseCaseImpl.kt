package com.ada.domain.usecases

import com.ada.common.debugLog
import com.ada.data.model.UserModel
import com.ada.data.repositories.AuthenticationRepository
import com.ada.data.repositories.RealTimeDatabaseRepository
import com.ada.domain.mapper.toUserOrNull
import com.ada.domain.model.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

private fun useCaseDebugLog(msg: () -> Any?) = debugLog(TAG = "GetCurrentUserUseCase", msg = msg)

class ObserveCurrentUserUseCaseImpl @Inject constructor(
    private val realTimeDatabaseRepository: RealTimeDatabaseRepository
) : ObserveCurrentUserUseCase {
    override fun invoke(): Flow<User?> = callbackFlow {

        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                useCaseDebugLog { "onDataChanged: $snapshot" }
                val userModel = snapshot.getValue<UserModel>()

                val user = userModel?.toUserOrNull()

                trySend(user)
                    .also { useCaseDebugLog { "tried send: $userModel" } }
            }

            override fun onCancelled(error: DatabaseError) {
                useCaseDebugLog { "onCancelled: $error" }
            }

        }

        realTimeDatabaseRepository.userRef().addValueEventListener(listener)
        awaitClose {
            realTimeDatabaseRepository.userRef().removeEventListener(listener)
        }
    }
}