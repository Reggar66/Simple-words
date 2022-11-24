package com.ada.simplewords.domain.repositories

import com.ada.simplewords.domain.models.QuizItemModel
import com.ada.simplewords.domain.models.User
import com.ada.simplewords.domain.models.WordTranslation
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject


@Module
@InstallIn(SingletonComponent::class)
class FirebaseRepository @Inject constructor() {
    /* TODO Firebase repository. */
    private val database =
        Firebase.database("https://simple-words-a3e96-default-rtdb.europe-west1.firebasedatabase.app/")

    private val userId = User.mockUserId() // TODO change to actual.

    private fun currentUserDatabaseRef() = database.getReference(userId)
    private fun userRef() = database.getReference("$userId/user")
    fun quizzesRef() = database.getReference("$userId/quizzes")
    private fun quizWordsRef() = database.getReference("$userId/quizWords")

    fun saveUser(user: User) {
        val key = currentUserDatabaseRef().push().key
        key?.let {
            userRef().setValue(user)
        }
    }

    fun saveQuiz(quizItemModel: QuizItemModel) {
        val key = quizzesRef().push().key
        key?.let {
            quizzesRef().child(it).setValue(quizItemModel.copy(id = key))
        }
    }

    fun saveQuizWords(quizId: String, words: List<WordTranslation>) {
        quizWordsRef().child(quizId).setValue(words)
    }

    fun getQuizzes() {
        quizzesRef().addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}