package com.ada.domain.usecases

import com.ada.domain.model.Quiz
import com.ada.domain.model.WordTranslation
import com.ada.domain.mapper.toQuizModel
import com.ada.domain.mapper.toWordTranslationModel
import com.ada.data.repositories.RealTimeDatabaseRepository
import javax.inject.Inject

class CreateQuizUseCaseImpl @Inject constructor(private val realTimeDatabaseRepository: RealTimeDatabaseRepository) :
    CreateQuizUseCase {

    override fun invoke(quiz: Quiz, words: List<WordTranslation>) {
        realTimeDatabaseRepository.saveQuizWithWords(
            quiz = quiz.toQuizModel(),
            words = words.map { it.toWordTranslationModel() })
    }
}