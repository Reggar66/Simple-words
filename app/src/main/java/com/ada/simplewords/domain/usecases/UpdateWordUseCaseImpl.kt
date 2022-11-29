package com.ada.simplewords.domain.usecases

import com.ada.simplewords.data.WordTranslation
import com.ada.simplewords.data.mapper.toWordTranslationModel
import com.ada.simplewords.domain.repositories.FirebaseRepository
import javax.inject.Inject

class UpdateWordUseCaseImpl @Inject constructor(private val firebaseRepository: FirebaseRepository) :
    UpdateWordUseCase {
    override fun invoke(wordTranslation: WordTranslation) {
        firebaseRepository.updateWord(wordTranslation.toWordTranslationModel())
    }
}