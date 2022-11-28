package com.ada.simplewords.feature.quiz.create

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.ada.simplewords.common.OnClickTakes
import com.ada.simplewords.ui.components.utility.PreviewContainer

@Composable
fun CreateQuizScreen() {
    val viewModel = hiltViewModel<CreateQuizViewModel>()

    CreateQuizScreenImpl {
        viewModel.createQuiz(it)
    }
}

@Composable
private fun CreateQuizScreenImpl(onAddClick: OnClickTakes<String>) {

    var name by remember {
        mutableStateOf("")
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text(text = "Name") } // TODO strings
        )
        Button(onClick = { onAddClick(name) }) {
            Icon(imageVector = Icons.Rounded.Check, contentDescription = null)
        }
    }
}

@Preview
@Composable
private fun CreateQuizPreview() {
    PreviewContainer {
        CreateQuizScreenImpl {

        }
    }
}