package com.ada.quizcreate

import android.widget.Toast
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.ada.common.SimpleNavigation
import com.ada.common.extensions.toast

@Composable
fun CreateQuizScreen(closeScreen: SimpleNavigation) {
    val viewModel = hiltViewModel<CreateQuizViewModel>()
    val createScreenState by viewModel.createScreenState.collectAsState()
    val context = LocalContext.current

    when (createScreenState.mode) {
        CreateScreenMode.Create -> {
            CreateQuizPanel(
                currentWords = viewModel.wordsToCreate,
                onCreateQuizClick = {
                    viewModel.createQuiz(it)
                    closeScreen()
                },
                onAddClick = { viewModel.addWordWithTranslation(it) },
                onRemoveClick = { viewModel.removeTranslation(it) },
                onApplyItemEdit = { idx, word ->
                    viewModel.update(idx = idx, word = word)
                },
                onImportClick = {
                    viewModel.changeMode(mode = CreateScreenMode.Import)
                }
            )
        }
        CreateScreenMode.Import -> {
            ImportPanel(
                onCancelClick = {
                    viewModel.changeMode(mode = CreateScreenMode.Create)
                },
                onImportClick = {
                    viewModel.import(
                        importString = it,
                        onComplete = { viewModel.changeMode(mode = CreateScreenMode.Create) },
                        onError = {
                            context.toast(
                                msg = "Could not import. Check import string.",
                                duration = Toast.LENGTH_LONG
                            )
                        })
                }
            )
        }
    }
}