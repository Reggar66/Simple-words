package com.ada.domain.usecases

import com.ada.domain.model.Quiz
import com.ada.domain.model.WordTranslation
import com.ada.domain.mapper.toQuizModel
import com.ada.domain.mapper.toWordTranslationModel
import com.ada.repositories.FirebaseRepository
import javax.inject.Inject

class CreateQuizUseCaseImpl @Inject constructor(private val firebaseRepository: FirebaseRepository) :
    CreateQuizUseCase {

    override fun invoke(quiz: Quiz, words: List<WordTranslation>) {
        firebaseRepository.saveQuizWithWords(
            quiz = quiz.toQuizModel(),
            words = words.map { it.toWordTranslationModel() })
    }
}