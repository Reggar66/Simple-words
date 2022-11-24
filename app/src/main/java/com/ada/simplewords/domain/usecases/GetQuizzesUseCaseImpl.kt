package com.ada.simplewords.domain.usecases

import com.ada.simplewords.common.debugLog
import com.ada.simplewords.domain.models.QuizItemModel
import com.ada.simplewords.domain.repositories.FirebaseRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


// TODO extract to separate class.
@Module
@InstallIn(SingletonComponent::class)
abstract class FirebaseModule {

    @Binds
    abstract fun bindGetQuizzesUseCase(
        getQuizzesUseCaseImpl: GetQuizzesUseCaseImpl
    ): GetQuizzesUseCase
}

class GetQuizzesUseCaseImpl @Inject constructor(firebaseRepository: FirebaseRepository) :
    GetQuizzesUseCase {

    private val quizzesRef = firebaseRepository.quizzesRef()

    override fun invoke(): Flow<List<QuizItemModel>> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val quizItemModel = snapshot.getValue<HashMap<String, QuizItemModel>>()
                debugLog { "Listener: $snapshot" }
                debugLog { "Listener: created data:$quizItemModel" }
                val quizzes = mutableListOf<QuizItemModel>()
                quizItemModel?.forEach { (key, quizz) ->
                    quizzes.add(quizz)
                }
                debugLog { "Listener: created list:$quizzes" }
                trySend(quizzes)
            }

            override fun onCancelled(error: DatabaseError) {
                //TODO("Not yet implemented")
            }
        }

        quizzesRef.addValueEventListener(listener)
        awaitClose {
            quizzesRef.removeEventListener(listener).also {
                debugLog { "Removed listener from $quizzesRef"}
            }
        }
    }
}