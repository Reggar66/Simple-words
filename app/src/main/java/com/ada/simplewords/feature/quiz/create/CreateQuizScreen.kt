package com.ada.simplewords.feature.quiz.create

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ada.simplewords.common.OnClickTakes
import com.ada.simplewords.ui.components.utility.PreviewContainer
import com.ada.simplewords.ui.navigation.SimpleNavigation
import kotlinx.coroutines.delay

@Composable
fun CreateQuizScreen(closeScreen: SimpleNavigation) {
    val viewModel = hiltViewModel<CreateQuizViewModel>()

    CreateQuizScreenImpl(
        currentWords = viewModel.wordsToCreate,
        onCreateQuizClick = {
            viewModel.createQuiz(it)
            closeScreen()
        },
        onAddClick = { viewModel.addWordWithTranslation(it) },
    )
}

@Composable
private fun CreateQuizScreenImpl(
    currentWords: List<WordWithTranslation>,
    onCreateQuizClick: OnClickTakes<String>,
    onAddClick: OnClickTakes<WordWithTranslation>
) {

    var name by remember {
        mutableStateOf("")
    }

    val lazyListState = rememberLazyListState()

    Column(modifier = Modifier.fillMaxSize()) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = name,
            onValueChange = { name = it },
            label = { Text(text = "Quiz Name") } // TODO strings
        )

        LazyColumn(
            modifier = Modifier.weight(1f),
            state = lazyListState,
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {

            /* List of already added */
            items(currentWords) { item ->
                Item(item = item)
            }

            item {
                AddItem(onAddClick = onAddClick)
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Bottom
        ) {
            Button(
                onClick = { onCreateQuizClick(name) },
                shape = CircleShape
            ) {
                Icon(imageVector = Icons.Rounded.Check, contentDescription = null)
            }
        }
    }

    val numberOfItems = remember { derivedStateOf { lazyListState.layoutInfo.totalItemsCount } }
    LaunchedEffect(key1 = numberOfItems.value, block = {
        delay(200)
        lazyListState.scrollToItem(lazyListState.layoutInfo.totalItemsCount - 1)
    })
}

@Composable
private fun Item(item: WordWithTranslation) {
    Card {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(modifier = Modifier.weight(1f), text = item.word)
            Text(modifier = Modifier.weight(1f), text = item.translation)
        }
    }
}

@Composable
private fun AddItem(onAddClick: OnClickTakes<WordWithTranslation>) {
    var word by remember {
        mutableStateOf("")
    }

    var translation by remember {
        mutableStateOf("")
    }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = word,
                onValueChange = { word = it },
                label = { Text(text = "Word") } // TODO strings
            )
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = translation,
                onValueChange = { translation = it },
                label = { Text(text = "Translation") } // TODO strings
            )
        }
        Button(
            onClick = {
                onAddClick(
                    WordWithTranslation(
                        word = word,
                        translation = translation
                    )
                )
            },
            shape = CircleShape
        ) {
            Text(text = "Add") // TODO: Strings
        }
    }
}

data class WordWithTranslation(val word: String, val translation: String) {
    companion object {
        fun mock(): List<WordWithTranslation> {
            val words = mutableListOf<WordWithTranslation>()

            for (i in 1..15) {
                words.add(WordWithTranslation(word = i.toString(), translation = i.toString()))
            }
            return words.toList()
        }
    }
}


@Preview(uiMode = UI_MODE_NIGHT_YES)
@Preview
@Composable
private fun CreateQuizPreview() {
    PreviewContainer {
        CreateQuizScreenImpl(
            currentWords = WordWithTranslation.mock(),
            onCreateQuizClick = {},
            onAddClick = {})
    }
}