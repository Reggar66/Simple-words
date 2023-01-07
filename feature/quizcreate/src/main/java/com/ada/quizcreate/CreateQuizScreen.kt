package com.ada.quizcreate

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ada.common.OnClick
import com.ada.common.OnClickTakes
import com.ada.common.SimpleNavigation
import com.ada.common.debugLog
import com.ada.ui.PreviewContainer
import com.ada.ui.components.SwipeMenu
import com.ada.ui.components.SwipeValue
import com.ada.ui.components.WordItem
import com.ada.ui.theme.itemBackground
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
        onRemoveClick = { viewModel.removeTranslation(it) },
        onApplyItemEdit = { idx, word ->
            viewModel.update(idx = idx, word = word)
        }
    )
}

@Composable
private fun CreateQuizScreenImpl(
    currentWords: List<WordWithTranslation>,
    onCreateQuizClick: OnClickTakes<String>,
    onAddClick: OnClickTakes<WordWithTranslation>,
    onRemoveClick: OnClickTakes<Int>,
    onApplyItemEdit: (ids: Int, word: WordWithTranslation) -> Unit
) {

    var name by remember {
        mutableStateOf("")
    }

    val lazyListState = rememberLazyListState()

    Column(modifier = Modifier.fillMaxSize()) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
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
            itemsIndexed(currentWords) { idx, item ->
                val isInEdit = remember { mutableStateOf(false) }
                Item(
                    editMode = isInEdit.value,
                    item = item,
                    onRemoveClick = { onRemoveClick(idx) },
                    onEditClick = { isInEdit.value = !isInEdit.value },
                    onApplyChanges = {
                        onApplyItemEdit(idx, it)
                        isInEdit.value = false
                    }
                )
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

    val lastSize = remember {
        mutableStateOf(lazyListState.layoutInfo.totalItemsCount)
    }

    val numberOfItems = remember { derivedStateOf { lazyListState.layoutInfo.totalItemsCount } }
    LaunchedEffect(key1 = numberOfItems.value, block = {
        debugLog { "${lastSize.value} | ${numberOfItems.value}" }
        if (lastSize.value <= numberOfItems.value) {
            delay(200)
            lazyListState.scrollToItem(lazyListState.layoutInfo.totalItemsCount - 1)
        }
        lastSize.value = numberOfItems.value
    })
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun Item(
    editMode: Boolean = false,
    item: WordWithTranslation,
    onRemoveClick: OnClick,
    onEditClick: OnClick,
    onApplyChanges: OnClickTakes<WordWithTranslation>
) {
    val state = rememberSwipeableState(initialValue = SwipeValue.Default)
    val scope = rememberCoroutineScope()
    SwipeMenu(state = state, onRemoveClick = onRemoveClick, onEditClick = {
        scope.launch {
            state.animateTo(SwipeValue.Default)
        }
        onEditClick()
    }) {
        if (!editMode)
            WordItem(leftText = item.word, rightText = item.translation)
        else
            EditWordItem(item = item, onApplyChanges = onApplyChanges)
    }
}

@Composable
private fun EditWordItem(
    item: WordWithTranslation,
    onApplyChanges: OnClickTakes<WordWithTranslation>
) {
    var word by remember { mutableStateOf(item.word) }
    var translation by remember { mutableStateOf(item.translation) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.itemBackground)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            modifier = Modifier.weight(1f),
            value = word,
            onValueChange = { word = it },
            label = { Text(text = "Word") } // TODO strings
        )
        Icon(modifier = Modifier.clickable {
            onApplyChanges(
                WordWithTranslation(
                    word = word,
                    translation = translation
                )
            )
        }, imageVector = Icons.Rounded.Check, contentDescription = null)
        OutlinedTextField(
            modifier = Modifier.weight(1f),
            value = translation,
            onValueChange = { translation = it },
            label = { Text(text = "Translation") } // TODO strings
        )
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
            onAddClick = {},
            onRemoveClick = {},
            onApplyItemEdit = { i: Int, wordWithTranslation: WordWithTranslation -> }
        )
    }
}