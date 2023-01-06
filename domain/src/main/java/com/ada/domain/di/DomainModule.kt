package com.ada.domain.di

import com.ada.domain.usecases.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {

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
    abstract fun bindObserveQuizUseCase(
        observeQuizUseImpl: ObserveQuizUseCaseImpl
    ): ObserveQuizUseCase

    @Binds
    abstract fun bindSignInAnonymousUserUseCase(
        signInAnonymousUserUseCase: SignInAnonymousUserUseCaseImpl
    ): SignInAnonymousUserUseCase

    @Binds
    abstract fun bindObserveCurrentUserUseCase(
        getCurrentUserUseCase: ObserveCurrentUserUseCaseImpl
    ): ObserveCurrentUserUseCase

    @Binds
    abstract fun bindSignOutUseCase(
        signOutUseCase: SignOutUseCaseImpl
    ): SignOutUseCase

    @Binds
    abstract fun bindSignUpEmailAndPasswordUseCase(
        signUpEmailAndPasswordUseCase: SignUpEmailAndPasswordUseCaseImpl
    ): SignUpEmailAndPasswordUseCase

    @Binds
    abstract fun bindConvertToPermanentUseCase(
        convertToPermanentUseCase: ConvertToPermanentUseCaseImpl
    ): ConvertToPermanentUseCase
}