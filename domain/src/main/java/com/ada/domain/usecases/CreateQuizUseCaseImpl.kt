package com.ada.domain.usecases

import com.ada.data.Quiz
import com.ada.data.WordTranslation
import com.ada.domain.mapper.toQuizModel
import com.ada.domain.mapper.toWordTranslationModel
import com.ada.domain.repositories.FirebaseRepository
import javax.inject.Inject

class CreateQuizUseCaseImpl @Inject constructor(private val firebaseRepository: FirebaseRepository) :
    CreateQuizUseCase {

    override fun invoke(quiz: Quiz, words: List<WordTranslation>) {
        firebaseRepository.saveQuizWithWords(
            quiz = quiz.toQuizModel(),
            words = words.map { it.toWordTranslationModel() })
    }
}