package com.ada.data.repositories

import com.ada.common.Key
import com.ada.common.debugLog
import com.ada.data.model.QuizModel
import com.ada.data.model.UserModel
import com.ada.data.model.WordTranslationModel
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

private const val TAG = "RealTimeDatabaseRepository"
private fun dbDebugLog(msg: () -> Any?) = debugLog(TAG = TAG, msg = msg)

@Module
@InstallIn(SingletonComponent::class)
class RealTimeDatabaseRepository @Inject constructor(private val authenticationRepository: AuthenticationRepository) {
    private val database =
        Firebase.database("https://simple-words-a3e96-default-rtdb.europe-west1.firebasedatabase.app/")

    private val userId
        get() = (authenticationRepository.getCurrentUser()?.uid
            ?: "").also { debugLog { "User Id DB: $it" } }

    private fun currentUserDatabaseRef() = database.getReference(userId)
    fun userRef() = database.getReference("$userId/user")
    fun quizzesRef() = database.getReference("$userId/quizzes")
    fun quizRef(quizId: Key) = database.getReference("$userId/quizzes/$quizId")
    private fun quizWordsRef() = database.getReference("$userId/quizWords")
    fun wordsRef(quizId: Key) = database.getReference("$userId/quizWords/$quizId")
    private fun wordRef(quizId: Key, wordId: Key) =
        database.getReference("$userId/quizWords/$quizId/$wordId")

    fun saveUser(userModel: UserModel, onSuccess: () -> Unit) {
        userRef().setValue(userModel).addOnSuccessListener {
            onSuccess()
        }
    }

    fun updateUser(userModel: UserModel) {
        userRef().updateChildren(userModel.toMap()) { error, ref ->
            debugLog(TAG) { "updateUser, onComplete: error: $error | ref: $ref" }
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

    fun removeQuizWithWords(quizId: Key) {
        quizzesRef().child(quizId).removeValue().addOnSuccessListener {
            dbDebugLog { "removeQuizWithWords: removed quiz with id $quizId" }
        }
        quizWordsRef().child(quizId).removeValue().addOnSuccessListener {
            dbDebugLog { "removeQuizWithWords: removed quiz words with id $quizId" }
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