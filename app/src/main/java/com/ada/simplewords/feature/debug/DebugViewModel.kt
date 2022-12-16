package com.ada.simplewords.feature.debug

import androidx.lifecycle.ViewModel
import com.example.domain.models.UserModel
import com.ada.simplewords.domain.repositories.FirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DebugViewModel @Inject constructor(private val firebaseRepository: FirebaseRepository) :
    ViewModel() {

    fun createMockUser() {
        firebaseRepository.saveUser(com.example.domain.models.UserModel.mock())
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