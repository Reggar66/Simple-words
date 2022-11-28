package com.ada.simplewords.domain.usecases

import com.ada.simplewords.common.debugLog
import com.ada.simplewords.data.WordTranslation
import com.ada.simplewords.data.toWordTranslationOrNull
import com.ada.simplewords.domain.models.WordTranslationModel
import com.ada.simplewords.domain.repositories.FirebaseRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class GetWordsUseCaseImpl @Inject constructor(private val firebaseRepository: FirebaseRepository) :
    GetWordsUseCase {

    override fun invoke(quizId: String): Flow<List<WordTranslation>> = callbackFlow {
        val wordsRef = firebaseRepository.wordsRef(quizId)

        // TODO rewrite to ChildEventListener
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                debugLog { "GetWordsUseCase: onDataChanged: $snapshot" }
                val wordTranslationModels =
                    snapshot.getValue<List<WordTranslationModel>>()
                val wordTranslations = mutableListOf<WordTranslation>()

                debugLog { snapshot.value?.javaClass }
                debugLog { snapshot.value }
                debugLog { wordTranslationModels }

                wordTranslationModels?.forEach { word ->
                    word.toWordTranslationOrNull()?.let {
                        wordTranslations.add(it)
                    }
                }


                trySend(wordTranslations).also {
                    debugLog { "GetWordsUseCase: tried send: $wordTranslations" }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                debugLog { "GetWordsUseCase: onCancelled: $error" }
            }
        }

        wordsRef.addValueEventListener(listener)
        awaitClose {
            wordsRef.removeEventListener(listener).also {
                debugLog { "GetWordsUseCase: Removed listener from $wordsRef" }
            }
        }
    }
}
