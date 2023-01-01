package com.ada.domain.usecases

import com.ada.common.Key
import com.ada.common.debugLog
import com.ada.domain.mapper.toWordTranslationOrNull
import com.ada.domain.model.WordTranslation
import com.ada.data.model.WordTranslationModel
import com.ada.data.repositories.RealTimeDatabaseRepository
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

private const val PREFIX = "ObserveWordsByOneUseCase:"

class ObserveWordsUseCaseImpl @Inject constructor(private val realTimeDatabaseRepository: RealTimeDatabaseRepository) :
    ObserveWordsUseCase {
    override fun invoke(quizId: String): Flow<WordResult> = callbackFlow {
        val wordsRef = realTimeDatabaseRepository.wordsRef(quizId)

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

                val wordTranslation =
                    snapshot.getValue<WordTranslationModel>()?.toWordTranslationOrNull()

                snapshot.key?.let { key ->
                    wordTranslation?.let { translation ->
                        trySend(WordResult(Event.Changed, key to translation)).also {
                            debugLog { "$PREFIX tried send: $wordTranslation" }
                        }
                    }
                }
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
            wordsRef.removeEventListener(listener).also {
                debugLog { "$PREFIX removed listener for quiz id: $quizId" }
            }
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