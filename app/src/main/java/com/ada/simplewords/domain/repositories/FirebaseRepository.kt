package com.ada.simplewords.domain.repositories

import com.ada.simplewords.domain.models.QuizItemData
import com.ada.simplewords.domain.models.User
import com.ada.simplewords.domain.models.WordTranslation
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
    val database =
        Firebase.database("https://simple-words-a3e96-default-rtdb.europe-west1.firebasedatabase.app/")

    private val userId = User.mockUserId() // TODO change to actual.

    private fun userDatabase() = database.getReference(userId)
    private fun userRef() = database.getReference("$userId/user")
    private fun quizzesRef() = database.getReference("$userId/quizzes")
    private fun quizWordsRef() = database.getReference("$userId/quizWords")

    fun saveUser(user: User) {
        val key = User.mockUserId() //usersRef.push().key
        key?.let {
            userRef().setValue(user)
        }
    }

    fun saveQuiz(quizItemData: QuizItemData) {
        val key = quizzesRef().push().key
        key?.let {
            quizzesRef().child(it).setValue(quizItemData.copy(id = key))
        }
    }

    fun saveQuizWords(quizId: String, words: List<WordTranslation>) {
        quizWordsRef().child(quizId).setValue(words)
    }
}