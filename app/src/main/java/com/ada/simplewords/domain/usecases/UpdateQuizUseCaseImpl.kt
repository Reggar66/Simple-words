package com.ada.simplewords.domain.usecases

import com.ada.data.Quiz
import com.ada.data.mapper.toQuizModel
import com.ada.simplewords.domain.repositories.FirebaseRepository
import javax.inject.Inject

class UpdateQuizUseCaseImpl @Inject constructor(private val firebaseRepository: FirebaseRepository) :
    UpdateQuizUseCase {
    override fun invoke(quiz: com.ada.data.Quiz) {
        firebaseRepository.updateQuiz(quiz.toQuizModel())
    }
}