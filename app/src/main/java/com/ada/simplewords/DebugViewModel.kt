package com.ada.simplewords

import androidx.lifecycle.ViewModel
import com.ada.data.model.UserModel
import com.ada.data.repositories.FirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DebugViewModel @Inject constructor(private val firebaseRepository: FirebaseRepository) :
    ViewModel() {

    fun createMockUser() {
        firebaseRepository.saveUser(UserModel.mock())
    }

    fun createMockQuizAnimals() {
        firebaseRepository.Debug().mockAnimals()
    }

    fun createMockQuizAnimalsCompleted() {
        firebaseRepository.Debug().mockAnimalsCompleted()
    }

    fun createMockQuizFood() {
        firebaseRepository.Debug().mockFood()
    }

    fun createMockQuizSeasons() {
        firebaseRepository.Debug().mockSeasons()
    }
}