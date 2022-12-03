package com.ada.simplewords.domain.usecases

import com.ada.simplewords.common.Key
import com.ada.simplewords.common.debugLog
import com.ada.simplewords.data.Quiz
import com.ada.simplewords.data.mapper.toQuizOrNull
import com.ada.simplewords.domain.models.QuizModel
import com.ada.simplewords.domain.repositories.FirebaseRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

private const val PREFIX = "ObserveQuizUseCase:"

class ObserveQuizUseCaseImpl @Inject constructor(private val firebaseRepository: FirebaseRepository) :
    ObserveQuizUseCase {
    override fun invoke(quizId: Key): Flow<Quiz> = callbackFlow {
        val quizRef = firebaseRepository.quizRef(quizId)

        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                debugLog { "$PREFIX onDataChange: $snapshot" }
                val quiz = snapshot.getValue<QuizModel>()?.toQuizOrNull()
                quiz?.let {
                    trySend(it).also {
                        debugLog { "$PREFIX tried send: $quiz" }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                debugLog { "$PREFIX onCancelled: $error" }
            }
        }

        quizRef.addValueEventListener(listener)
        awaitClose {
            quizRef.removeEventListener(listener)
        }
    }
}