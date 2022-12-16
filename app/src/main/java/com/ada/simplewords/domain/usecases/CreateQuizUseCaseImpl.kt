package com.ada.simplewords.domain.usecases

import com.ada.data.Quiz
import com.ada.data.WordTranslation
import com.ada.data.mapper.toQuizModel
import com.ada.data.mapper.toWordTranslationModel
import com.ada.simplewords.domain.repositories.FirebaseRepository
import javax.inject.Inject

class CreateQuizUseCaseImpl @Inject constructor(private val firebaseRepository: FirebaseRepository) :
    CreateQuizUseCase {

    override fun invoke(quiz: com.ada.data.Quiz, words: List<com.ada.data.WordTranslation>) {
        firebaseRepository.saveQuizWithWords(
            quiz = quiz.toQuizModel(),
            words = words.map { it.toWordTranslationModel() })
    }
}