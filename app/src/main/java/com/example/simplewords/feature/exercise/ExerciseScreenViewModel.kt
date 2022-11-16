package com.example.simplewords.feature.exercise

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ExerciseScreenViewModel @Inject constructor(private val fakeRepo: FakeRepo) : ViewModel() {


    fun repoCall() = fakeRepo.repoCall()
}