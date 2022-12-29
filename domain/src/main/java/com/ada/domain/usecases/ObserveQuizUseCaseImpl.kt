package com.ada.domain.usecases

import com.ada.common.Key
import com.ada.common.debugLog
import com.ada.domain.model.Quiz
import com.ada.domain.mapper.toQuizOrNull
import com.ada.data.model.QuizModel
import com.ada.data.repositories.FirebaseRepository
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
            quizRef.removeEventListener(listener).also {
                debugLog { "$PREFIX removed listener for quiz id: $quizId" }
            }
        }
    }
}