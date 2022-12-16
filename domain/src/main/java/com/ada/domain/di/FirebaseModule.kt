package com.ada.domain.di

import com.ada.domain.usecases.*
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

    @Binds
    abstract fun bindObserveWordsUseCase(
        observeWordsUseCaseImpl: ObserveWordsUseCaseImpl
    ): ObserveWordsUseCase

    @Binds
    abstract fun bindCreateQuiz(
        createQuizUseCaseImpl: CreateQuizUseCaseImpl
    ): CreateQuizUseCase

    @Binds
    abstract fun bindUpdateWordUseCase(
        updateWordUseCaseImpl: UpdateWordUseCaseImpl
    ): UpdateWordUseCase

    @Binds
    abstract fun bindUpdateQuizUseCase(
        updateQuizUseCaseImpl: UpdateQuizUseCaseImpl
    ): UpdateQuizUseCase

    @Binds
    abstract fun bindObserveQuizUse(
        ObserveQuizUseImpl: ObserveQuizUseCaseImpl
    ): ObserveQuizUseCase
}