package com.ada.domain.usecases

import com.ada.domain.model.Quiz
import com.ada.domain.mapper.toQuizModel
import com.ada.data.repositories.FirebaseRepository
import javax.inject.Inject

class UpdateQuizUseCaseImpl @Inject constructor(private val firebaseRepository: FirebaseRepository) :
    UpdateQuizUseCase {
    override fun invoke(quiz: Quiz) {
        firebaseRepository.updateQuiz(quiz.toQuizModel())
    }
}