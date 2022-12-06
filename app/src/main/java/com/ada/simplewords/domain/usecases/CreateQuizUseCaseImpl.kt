package com.ada.simplewords.domain.usecases

import com.ada.simplewords.data.Quiz
import com.ada.simplewords.data.WordTranslation
import com.ada.simplewords.data.mapper.toQuizModel
import com.ada.simplewords.data.mapper.toWordTranslationModel
import com.ada.simplewords.domain.repositories.FirebaseRepository
import javax.inject.Inject

class CreateQuizUseCaseImpl @Inject constructor(private val firebaseRepository: FirebaseRepository) :
    CreateQuizUseCase {

    override fun invoke(quiz: Quiz, words: List<WordTranslation>) {
        firebaseRepository.saveQuizWithWords(
            quiz = quiz.toQuizModel(),
            words = words.map { it.toWordTranslationModel() })
    }
}