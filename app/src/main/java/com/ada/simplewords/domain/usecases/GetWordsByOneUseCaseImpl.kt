package com.ada.simplewords.domain.usecases

import com.ada.simplewords.common.debugLog
import com.ada.simplewords.data.WordTranslation
import com.ada.simplewords.data.toWordTranslationOrNull
import com.ada.simplewords.domain.models.WordTranslationModel
import com.ada.simplewords.domain.repositories.FirebaseRepository
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

private const val PREFIX = "GetWordsByOneUseCase:"

typealias Key = String // TODO move to somewhere.

// TODO Change name to observe?
class GetWordsByOneUseCaseImpl @Inject constructor(private val firebaseRepository: FirebaseRepository) :
    GetWordsByOneUseCase {
    override fun invoke(quizId: String): Flow<WordResult> = callbackFlow {
        val wordsRef = firebaseRepository.wordsRef(quizId)

        val listener = object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                debugLog { "$PREFIX onChildAdded: $snapshot" }

                val wordTranslation =
                    snapshot.getValue<WordTranslationModel>()?.toWordTranslationOrNull()

                snapshot.key?.let { key ->
                    wordTranslation?.let { translation ->
                        trySend(WordResult(Event.Added, key to translation)).also {
                            debugLog { "$PREFIX tried send: $wordTranslation" }
                        }
                    }
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                debugLog { "$PREFIX onChildChanged: $snapshot" }
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                debugLog { "$PREFIX onChildRemoved: $snapshot" }

                val wordTranslation =
                    snapshot.getValue<WordTranslationModel>()?.toWordTranslationOrNull()

                snapshot.key?.let { key ->
                    wordTranslation?.let { translation ->
                        trySend(WordResult(Event.Removed, key to translation)).also {
                            debugLog { "$PREFIX tried send: $wordTranslation" }
                        }
                    }
                }
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                debugLog { "$PREFIX onChildMoved: $snapshot" }
            }

            override fun onCancelled(error: DatabaseError) {
                debugLog { "$PREFIX onCancelled: $error" }
            }
        }

        wordsRef.addChildEventListener(listener)
        awaitClose {
            wordsRef.removeEventListener(listener)
        }
    }
}

enum class Event {
    Added,
    Changed,
    Removed,
    Moved
}

data class WordResult(val event: Event, val word: Pair<Key, WordTranslation>)