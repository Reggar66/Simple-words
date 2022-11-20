package com.ada.simplewords.domain.repositories

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
    val myRef = database.getReference("message")

    fun doStuff() {
        myRef.setValue("Hello, World!")
    }
}