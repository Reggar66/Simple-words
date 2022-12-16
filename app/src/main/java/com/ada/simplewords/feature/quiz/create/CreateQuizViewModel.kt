package com.ada.simplewords.feature.quiz.create

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.ada.data.Quiz
import com.ada.data.WordTranslation
import com.ada.simplewords.domain.usecases.CreateQuizUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateQuizViewModel @Inject constructor(private val createQuizUseCase: CreateQuizUseCase) :
    ViewModel() {

    private val _wordsToCreate = mutableStateListOf<WordWithTranslation>()
    val wordsToCreate get() = _wordsToCreate.toList()

    init {
        _wordsToCreate.addAll(WordWithTranslation.mock())
    }

    fun createQuiz(name: String) {
        createQuizUseCase.invoke(
            quiz = com.ada.data.Quiz.empty().copy(name = name, wordsNumber = wordsToCreate.size),
            words = wordsToCreate.map {
                com.ada.data.WordTranslation.empty().copy(word = it.word, translation = it.translation)
            }
        )
    }

    fun addWordWithTranslation(word: WordWithTranslation) {
        _wordsToCreate.add(word)
    }

    fun removeTranslation(index: Int) {
        _wordsToCreate.removeAt(index)
    }
}