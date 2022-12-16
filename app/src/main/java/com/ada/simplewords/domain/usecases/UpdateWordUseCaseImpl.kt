package com.ada.simplewords.domain.usecases

import com.ada.data.WordTranslation
import com.ada.data.mapper.toWordTranslationModel
import com.ada.simplewords.domain.repositories.FirebaseRepository
import javax.inject.Inject

class UpdateWordUseCaseImpl @Inject constructor(private val firebaseRepository: FirebaseRepository) :
    UpdateWordUseCase {
    override fun invoke(wordTranslation: com.ada.data.WordTranslation) {
        firebaseRepository.updateWord(wordTranslation.toWordTranslationModel())
    }
}