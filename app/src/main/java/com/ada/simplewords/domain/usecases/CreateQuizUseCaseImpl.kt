package com.ada.simplewords.domain.usecases

import com.ada.simplewords.data.Quiz
import com.ada.simplewords.data.mapper.toQuizModel
import com.ada.simplewords.domain.repositories.FirebaseRepository
import javax.inject.Inject

class CreateQuizUseCaseImpl @Inject constructor(private val firebaseRepository: FirebaseRepository) :
    CreateQuizUseCase {

    override fun invoke(quiz: Quiz) {
        firebaseRepository.saveQuiz(quiz.toQuizModel())
    }
}