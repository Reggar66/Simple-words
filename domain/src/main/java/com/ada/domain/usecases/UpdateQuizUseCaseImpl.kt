package com.ada.domain.usecases

import com.ada.domain.model.Quiz
import com.ada.domain.mapper.toQuizModel
import com.ada.data.repositories.RealTimeDatabaseRepository
import javax.inject.Inject

class UpdateQuizUseCaseImpl @Inject constructor(private val realTimeDatabaseRepository: RealTimeDatabaseRepository) :
    UpdateQuizUseCase {
    override fun invoke(quiz: Quiz) {
        realTimeDatabaseRepository.updateQuiz(quiz.toQuizModel())
    }
}