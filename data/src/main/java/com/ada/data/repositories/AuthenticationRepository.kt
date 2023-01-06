package com.ada.data.repositories

import com.ada.common.debugLog
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject

private const val TAG = "AuthenticationRepository"
private fun authDebugLog(msg: () -> Any?) = debugLog(TAG = TAG, msg = msg)

@Module
@InstallIn(SingletonComponent::class)
class AuthenticationRepository @Inject constructor() {
    private var auth = Firebase.auth

    /**
     * Checks if user is currently signed in.
     */
    fun isSignedIn(): Boolean {
        return auth.currentUser != null
    }

    /**
     * Returns currently signed in user or null if not signed in.
     */
    fun getCurrentUser() = auth.currentUser

    /**
     * Registers new user with email and password.
     * [onComplete] block returns [FirebaseUser] on success or null on failure.
     */
    fun createUserWithEmail(
        email: String,
        password: String,
        onComplete: (user: FirebaseUser?) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            when {
                task.isSuccessful -> {
                    authDebugLog { "createUserWithEmail: Success." }
                    authDebugLog { "createUserWithEmail: is anonymous: ${auth.currentUser?.isAnonymous}" }
                    onComplete(auth.currentUser)
                }
                else -> {
                    authDebugLog { "createUserWithEmail: Failure." }
                    onComplete(null)
                }
            }
        }
    }

    /**
     * Signs in user with email and password.
     * [onComplete] block returns [FirebaseUser] on success or null on failure.
     */
    fun signInWithEmail(
        email: String,
        password: String,
        onComplete: (user: FirebaseUser?) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            when {
                task.isSuccessful -> {
                    authDebugLog { "signInWithEmail: Success." }
                    onComplete(auth.currentUser)
                }
                else -> {
                    authDebugLog { "signInWithEmail: Failure." }
                    onComplete(null)
                }
            }
        }
    }

    /**
     * Creates anonymous account and signs in user.
     * If user is already signed in as anonymous it wont create a new one.
     * [onComplete] block returns [FirebaseUser] on success or null on failure.
     */
    fun signInAnonymously(onComplete: (user: FirebaseUser?) -> Unit) {
        auth.signInAnonymously().addOnCompleteListener { task ->
            when {
                task.isSuccessful -> {
                    authDebugLog { "signInAnonymously: Success." }
                    onComplete(auth.currentUser)
                }
                else -> {
                    authDebugLog { "signInAnonymously: Failure." }
                    onComplete(null)
                }
            }
        }
    }

    /**
     * Converts anonymous account to permanent account.
     * You should create new account to get credentials but do not sign in before converting.
     * @param credential credentials of newly created account
     */
    fun convertAnonymousToPermanentAccount(
        credential: AuthCredential,
        onComplete: (user: FirebaseUser?) -> Unit
    ) {

        auth.currentUser.let { anonUser ->
            when (anonUser) {
                null -> {
                    authDebugLog { "convertAnonymousToPermanentAccount: current user is null. Did you forget to log in as anonymous?" }
                }
                else -> {
                    anonUser.linkWithCredential(credential).addOnCompleteListener { task ->
                        when {
                            task.isSuccessful -> {
                                authDebugLog { "convertAnonymousToPermanentAccount: Success." }
                                val user = task.result?.user
                                onComplete(user)
                            }

                            else -> {
                                authDebugLog { "convertAnonymousToPermanentAccount: Failure." }
                                onComplete(null)
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Signs out user.
     */
    fun signOut() {
        auth.signOut()
    }
}