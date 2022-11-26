package com.ada.simplewords.common.module.binders

import com.ada.simplewords.domain.usecases.GetQuizzesUseCase
import com.ada.simplewords.domain.usecases.GetQuizzesUseCaseImpl
import com.ada.simplewords.domain.usecases.GetWordsUseCase
import com.ada.simplewords.domain.usecases.GetWordsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class FirebaseModule {

    @Binds
    abstract fun bindGetQuizzesUseCase(
        getQuizzesUseCaseImpl: GetQuizzesUseCaseImpl
    ): GetQuizzesUseCase

    @Binds
    abstract fun bindGetWordsUseCase(
        getWordsUseCaseImpl: GetWordsUseCaseImpl
    ): GetWordsUseCase
}