package com.ada.domain.usecases

import com.ada.common.Key

interface RemoveWordUseCase {
    suspend operator fun invoke(quizId: Key, wordId: Key): Boolean
}