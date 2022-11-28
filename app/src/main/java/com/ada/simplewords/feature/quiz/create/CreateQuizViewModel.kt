package com.ada.simplewords.feature.quiz.create

import androidx.lifecycle.ViewModel
import com.ada.simplewords.data.Quiz
import com.ada.simplewords.domain.usecases.CreateQuizUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateQuizViewModel @Inject constructor(private val createQuizUseCase: CreateQuizUseCase) :
    ViewModel() {

    fun createQuiz(name: String) {
        createQuizUseCase.invoke(Quiz.empty().copy(name = name))
    }
}