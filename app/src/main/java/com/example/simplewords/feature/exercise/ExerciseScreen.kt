package com.example.simplewords.feature.exercise

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.simplewords.ui.components.utility.PreviewContainer

/* TODO Exercise screen.*/

@Composable
fun ExerciseScreen(quizId: Int?) {
    // TODO viewModel stuff.

    ExerciseScreenImpl()
}

@Composable
private fun ExerciseScreenImpl() {
    Column(modifier = Modifier.fillMaxSize()) {
        Card(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            QuestionCard(question = "Cat")
        }
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Correct!")
        }
        Card(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            AnswerCard()
        }
    }
}

@Composable
private fun QuestionCard(question: String) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(modifier = Modifier.align(Alignment.Center), text = question)
    }
}

@Composable
private fun AnswerCard() {
    var inputText by remember {
        mutableStateOf("")
    }
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Box(modifier = Modifier.weight(1f)) {
            OutlinedTextField(modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center),
                value = inputText,
                onValueChange = { inputText = it },
                label = { Text(text = "Answer") })
        }
        Button(onClick = { /*TODO*/ }) {
            Text(text = "Submit")
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Preview
@Composable
private fun ExerciseScreenPreview() {
    PreviewContainer() {
        ExerciseScreenImpl()
    }
}