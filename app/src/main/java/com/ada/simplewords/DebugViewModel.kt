package com.ada.simplewords

import androidx.lifecycle.ViewModel
import com.ada.data.model.UserModel
import com.ada.data.repositories.AuthenticationRepository
import com.ada.data.repositories.RealTimeDatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DebugViewModel @Inject constructor(
    private val realTimeDatabaseRepository: RealTimeDatabaseRepository,
    private val authenticationRepository: AuthenticationRepository
) :
    ViewModel() {

    fun createMockQuizAnimals() {
        realTimeDatabaseRepository.Debug().mockAnimals()
    }

    fun createMockQuizAnimalsCompleted() {
        realTimeDatabaseRepository.Debug().mockAnimalsCompleted()
    }

    fun createMockQuizFood() {
        realTimeDatabaseRepository.Debug().mockFood()
    }

    fun createMockQuizSeasons() {
        realTimeDatabaseRepository.Debug().mockSeasons()
    }

    fun signOut() {
        authenticationRepository.signOut()
    }
}