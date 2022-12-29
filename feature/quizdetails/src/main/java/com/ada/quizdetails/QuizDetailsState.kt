package com.ada.quizdetails

import com.ada.common.Key
import com.ada.data.model.WordTranslationModel
import com.ada.domain.mapper.toWordTranslationOrNull
import com.ada.domain.model.Quiz
import com.ada.domain.model.WordTranslation

data class QuizDetailsState(val quiz: Quiz?, val words: List<Pair<Key, WordTranslation>>) {
    companion object {
        fun empty() = QuizDetailsState(quiz = Quiz.empty(), words = emptyList())

        fun mock() = empty().copy(
            quiz = Quiz.mockQuizzes().first(),
            words = WordTranslationModel.mockAnimals.let {
                val map = mutableMapOf<Key, WordTranslation>()
                it.forEach { wordTranslationModel ->
                    wordTranslationModel.toWordTranslationOrNull()
                        ?.let { translation: WordTranslation ->
                            map[translation.id] = translation
                        }
                }
                return@let map.toList()
            }
        )
    }
}