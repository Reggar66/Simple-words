package com.ada.domain.usecases

import com.ada.domain.model.WordTranslation
import com.ada.domain.mapper.toWordTranslationModel
import com.ada.data.repositories.FirebaseRepository
import javax.inject.Inject

class UpdateWordUseCaseImpl @Inject constructor(private val firebaseRepository: FirebaseRepository) :
    UpdateWordUseCase {
    override fun invoke(wordTranslation: WordTranslation) {
        firebaseRepository.updateWord(wordTranslation.toWordTranslationModel())
    }
}