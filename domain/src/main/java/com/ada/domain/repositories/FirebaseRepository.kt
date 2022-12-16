package com.ada.domain.repositories

import com.ada.common.Key
import com.ada.common.debugLog
import com.ada.domain.models.QuizModel
import com.ada.domain.models.UserModel
import com.ada.domain.models.WordTranslationModel
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

private const val TAG = "FirebaseRepository"

@Module
@InstallIn(SingletonComponent::class)
class FirebaseRepository @Inject constructor() {
    private val database =
        Firebase.database("https://simple-words-a3e96-default-rtdb.europe-west1.firebasedatabase.app/")

    private val userId = UserModel.mockUserId() // TODO change to actual.

    private fun currentUserDatabaseRef() = database.getReference(userId)
    private fun userRef() = database.getReference("$userId/user")
    fun quizzesRef() = database.getReference("$userId/quizzes")
    fun quizRef(quizId: Key) = database.getReference("$userId/quizzes/$quizId")
    private fun quizWordsRef() = database.getReference("$userId/quizWords")
    fun wordsRef(quizId: Key) = database.getReference("$userId/quizWords/$quizId")
    private fun wordRef(quizId: Key, wordId: Key) =
        database.getReference("$userId/quizWords/$quizId/$wordId")

    fun saveUser(userModel: UserModel) {
        val key = currentUserDatabaseRef().push().key
        key?.let {
            userRef().setValue(userModel)
        }
    }

    /**
     * Saves [QuizModel] to database with newly generated key.
     * @return String key for saved quiz or null if unsuccessful.
     */
    fun saveQuiz(quizModel: QuizModel): Key? {
        val key = quizzesRef().push().key
        key?.let {
            quizzesRef().child(it).setValue(quizModel.copy(id = key))
        }
        return key
    }

    fun saveQuizWithWords(quiz: QuizModel, words: List<WordTranslationModel>) {
        val key = quizzesRef().push().key
        key?.let {
            quizzesRef().child(key).setValue(quiz.copy(id = key))
            saveQuizWords(key, words)
        }
    }

    fun updateQuiz(quizModel: QuizModel) {
        quizModel.apply {
            id?.let { id ->
                quizRef(id).updateChildren(quizModel.toMap()) { error, ref ->
                    debugLog(TAG) { "updateQuiz, onComplete: error: $error | ref: $ref" }
                }
            }
        }
    }

    fun saveQuizWords(quizId: Key, words: List<WordTranslationModel>) {
        words.forEach {
            saveQuizWord(quizId, it)
        }
    }

    /**
     * Saves [WordTranslationModel] to database with generated and applied unique [Key],
     * also applies given quizId.
     */
    fun saveQuizWord(quizId: Key, word: WordTranslationModel): Key? {
        val key = quizWordsRef().push().key
        key?.let {
            quizWordsRef().child(quizId).child(key)
                .setValue(word.copy(id = key, quizItemId = quizId))
        }
        return key
    }

    fun updateWord(wordTranslationModel: WordTranslationModel) {
        wordTranslationModel.apply {
            quizItemId?.let { quizId ->
                id?.let { wordId ->
                    wordRef(quizId, wordId).updateChildren(
                        wordTranslationModel.toMap()
                    ) { error, ref ->
                        debugLog(TAG) { "updateWord, onComplete: error: $error | ref: $ref" }
                    }
                }
            }
        }
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