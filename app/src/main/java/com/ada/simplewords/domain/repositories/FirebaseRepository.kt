package com.ada.simplewords.domain.repositories

import com.ada.simplewords.domain.models.QuizModel
import com.ada.simplewords.domain.models.UserModel
import com.ada.simplewords.domain.models.WordTranslationModel
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

    private val userId = UserModel.mockUserId() // TODO change to actual.

    private fun currentUserDatabaseRef() = database.getReference(userId)
    private fun userRef() = database.getReference("$userId/user")
    fun quizzesRef() = database.getReference("$userId/quizzes")
    private fun quizWordsRef() = database.getReference("$userId/quizWords")
    fun wordsRef(quizId: String) = database.getReference("$userId/quizWords/$quizId")

    fun saveUser(userModel: UserModel) {
        val key = currentUserDatabaseRef().push().key
        key?.let {
            userRef().setValue(userModel)
        }
    }

    /**
     * Saves [QuizModel] to database.
     * @return String key for saved quiz or null if unsuccessful.
     */
    fun saveQuiz(quizModel: QuizModel): String? {
        val key = quizzesRef().push().key
        key?.let {
            quizzesRef().child(it).setValue(quizModel.copy(id = key))
        }
        return key
    }

    fun saveQuizWords(quizId: String, words: List<WordTranslationModel>) {
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

    inner class Debug {
        fun mockAnimals() {
            val quizKey = saveQuiz(QuizModel.mockAnimals)
            quizKey?.let { key ->
                saveQuizWords(
                    key,
                    WordTranslationModel.mockAnimals.map { it.copy(quizItemId = key) })
            }
        }

        fun mockAnimalsCompleted() {
            val quizKey = saveQuiz(QuizModel.mockAnimalsCompleted)
            quizKey?.let { key ->
                saveQuizWords(
                    key,
                    WordTranslationModel.mockAnimalsCompleted.map { it.copy(quizItemId = key) })
            }
        }

        fun mockFood() {
            val quizKey = saveQuiz(QuizModel.mockFood)
            quizKey?.let { key ->
                saveQuizWords(
                    key,
                    WordTranslationModel.mockFoodCompleted.map { it.copy(quizItemId = key) })
            }
        }

        fun mockSeasons() {
            val quizKey = saveQuiz(QuizModel.mockSeasons)
            quizKey?.let { key ->
                saveQuizWords(
                    key,
                    WordTranslationModel.mockSeasons.map { it.copy(quizItemId = key) })
            }
        }
    }
}