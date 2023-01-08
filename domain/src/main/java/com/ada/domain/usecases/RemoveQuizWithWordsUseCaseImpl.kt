package com.ada.domain.usecases

import com.ada.data.repositories.RealTimeDatabaseRepository
import com.ada.domain.model.Quiz
import javax.inject.Inject

class RemoveQuizWithWordsUseCaseImpl @Inject constructor(
    private val realTimeDatabaseRepository: RealTimeDatabaseRepository
) : RemoveQuizWithWordsUseCase {
    override fun invoke(quiz: Quiz) {
        realTimeDatabaseRepository.removeQuizWithWords(quiz.id)
    }
}