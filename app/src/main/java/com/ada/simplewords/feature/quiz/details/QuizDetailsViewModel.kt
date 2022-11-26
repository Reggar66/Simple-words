package com.ada.simplewords.feature.quiz.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ada.simplewords.data.WordTranslation
import com.ada.simplewords.domain.usecases.GetWordsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizDetailsViewModel @Inject constructor(private val getWordsUseCase: GetWordsUseCase) :
    ViewModel() {

    var words by mutableStateOf<List<WordTranslation>>(emptyList())

    fun getTranslationsForId(quizId: String) = viewModelScope.launch {
        getWordsUseCase(quizId = quizId).collect {
            words = it
        }
    }

}