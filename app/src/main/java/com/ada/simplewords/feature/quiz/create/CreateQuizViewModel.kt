package com.ada.simplewords.feature.quiz.create

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.ada.domain.model.Quiz
import com.ada.domain.model.WordTranslation
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreateQuizViewModel @Inject constructor(private val createQuizUseCase: com.ada.domain.usecases.CreateQuizUseCase) :
    ViewModel() {

    private val _wordsToCreate = mutableStateListOf<WordWithTranslation>()
    val wordsToCreate get() = _wordsToCreate.toList()

    init {
        _wordsToCreate.addAll(WordWithTranslation.mock())
    }

    fun createQuiz(name: String) {
        createQuizUseCase.invoke(
            quiz = Quiz.empty().copy(name = name, wordsNumber = wordsToCreate.size),
            words = wordsToCreate.map {
                WordTranslation.empty().copy(word = it.word, translation = it.translation)
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