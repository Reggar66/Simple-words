package com.ada.quizcreate

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ada.common.debugLog
import com.ada.domain.model.Quiz
import com.ada.domain.model.WordTranslation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CreateQuizViewModel @Inject constructor(private val createQuizUseCase: com.ada.domain.usecases.CreateQuizUseCase) :
    ViewModel() {

    private val _createScreenState = MutableStateFlow(CreateScreenState.empty())
    private val _wordsToCreate = mutableStateListOf<WordWithTranslation>()

    val wordsToCreate get() = _wordsToCreate.toList()
    val createScreenState get() = _createScreenState.asStateFlow()

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

    fun update(idx: Int, word: WordWithTranslation) {
        _wordsToCreate.set(index = idx, element = word)
    }

    fun changeMode(mode: CreateScreenMode) {
        _createScreenState.update { it.copy(mode = mode) }
    }

    fun import(importString: String, onComplete: () -> Unit, onError: () -> Unit) =
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val temp = mutableListOf<WordWithTranslation>()
                importString.split("\n").forEach {
                    val (word, translation) = it.split(";")
                    temp.add(WordWithTranslation(word = word, translation = translation))
                }
                _wordsToCreate.addAll(temp)
                withContext(Dispatchers.Main) {
                    onComplete()
                }
            } catch (e: Exception) {
                debugLog { "CreateQuizViewModel: import: $e" }
                withContext(Dispatchers.Main) {
                    onError()
                }
            }
        }
}