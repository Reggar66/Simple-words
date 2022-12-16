package com.ada.simplewords.domain.usecases

import com.ada.simplewords.common.debugLog
import com.ada.data.Quiz
import com.ada.data.mapper.toQuizOrNull
import com.example.domain.models.QuizModel
import com.ada.simplewords.domain.repositories.FirebaseRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class GetQuizzesUseCaseImpl @Inject constructor(firebaseRepository: FirebaseRepository) :
    GetQuizzesUseCase {

    private val quizzesRef = firebaseRepository.quizzesRef()

    override fun invoke(): Flow<List<com.ada.data.Quiz>> = callbackFlow {

        // TODO rewrite to ChildEventListener
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                debugLog { "GetQuizzesUseCase: onDataChanged: $snapshot" }

                val quizModel = snapshot.getValue<HashMap<String, com.example.domain.models.QuizModel>>()
                val quizzes = mutableListOf<com.ada.data.Quiz>()
                quizModel?.forEach { (key, quizz) ->
                    quizz.toQuizOrNull()?.let {
                        quizzes.add(it)
                    }
                }
                trySend(quizzes).also {
                    debugLog { "GetQuizzesUseCase: tried send: $quizzes" }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                debugLog { "GetQuizzesUseCase: onCancelled: $error" }
            }
        }

        quizzesRef.addValueEventListener(listener)
        awaitClose {
            quizzesRef.removeEventListener(listener).also {
                debugLog { "GetQuizzesUseCase: Removed listener from $quizzesRef" }
            }
        }
    }
}