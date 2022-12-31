package com.ada.exercise.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ada.common.OnClick
import com.ada.common.OnClickTakes
import com.ada.exercise.ValidationState
import com.ada.ui.PreviewContainer
import com.ada.ui.theme.exerciseWord

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun AnswerPanelClassic(
    modifier: Modifier = Modifier,
    validationState: ValidationState,
    onValidate: OnClickTakes<String>,
    onNextClick: OnClick
) {
    var inputText by remember {
        mutableStateOf("")
    }

    LaunchedEffect(key1 = validationState, block = {
        if (validationState == ValidationState.WAITING)
            inputText = ""
    })

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier
            .weight(1f)
            .onKeyEvent {
                // Applies answer by pressing enter key on physical keyboard.
                if (it.key == Key.Enter) {
                    onValidate(inputText)
                    true
                } else false
            }) {
            if (validationState == ValidationState.WAITING)
                AnswerInput(
                    inputText = inputText,
                    onValueChanged = { inputText = it },
                    onDone = { onValidate(inputText) })
            else
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                        .padding(horizontal = 8.dp),
                    text = inputText,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.exerciseWord
                )
        }

        if (validationState != ValidationState.WAITING)
            Button(onClick = { onNextClick() }) {
                Text(text = "Next") // TODO: strings.xml
            }
    }
}

@Composable
private fun BoxScope.AnswerInput(
    inputText: String,
    onValueChanged: (String) -> Unit,
    onDone: (KeyboardActionScope.() -> Unit)
) {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .align(Alignment.Center),
        value = inputText,
        onValueChange = onValueChanged,
        label = { Text(text = "Answer") }, // TODO: strings.xml
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = onDone,
            onGo = {},
            onNext = {},
            onPrevious = {},
            onSearch = {},
            onSend = {}
        )
    )
}

@Preview
@Composable
private fun AnswerCardPreview() {
    PreviewContainer {
        AnswerPanelClassic(
            modifier = Modifier.size(200.dp),
            validationState = ValidationState.WAITING,
            onValidate = {}) {

        }
    }
}