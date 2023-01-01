package com.ada.domain.usecases

import com.ada.domain.model.WordTranslation
import com.ada.domain.mapper.toWordTranslationModel
import com.ada.data.repositories.RealTimeDatabaseRepository
import javax.inject.Inject

class UpdateWordUseCaseImpl @Inject constructor(private val realTimeDatabaseRepository: RealTimeDatabaseRepository) :
    UpdateWordUseCase {
    override fun invoke(wordTranslation: WordTranslation) {
        realTimeDatabaseRepository.updateWord(wordTranslation.toWordTranslationModel())
    }
}