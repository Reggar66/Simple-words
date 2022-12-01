package com.ada.simplewords.feature.quiz.details

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ada.simplewords.common.Key
import com.ada.simplewords.common.OnClick
import com.ada.simplewords.data.WordTranslation
import com.ada.simplewords.data.toWordTranslationOrEmpty
import com.ada.simplewords.domain.models.WordTranslationModel
import com.ada.simplewords.ui.components.utility.PreviewContainer
import com.ada.simplewords.ui.components.WordItem

@Composable
fun QuizDetailsScreen(quizId: String?, onLearnClick: OnClick) {
    val viewModel = hiltViewModel<QuizDetailsViewModel>()

    LaunchedEffect(
        key1 = quizId,
        block = {
            quizId?.let {
                viewModel.observeWords(it)
            }
        }
    )

    QuizDetails(viewModel.hashWords.toList(), onLearnClick = onLearnClick)
}

@Composable
private fun QuizDetails(words: List<Pair<Key, WordTranslation>>, onLearnClick: OnClick) {
    Box(modifier = Modifier.fillMaxWidth()) {
        LazyColumn(
            contentPadding = PaddingValues(start = 8.dp, top = 8.dp, end = 8.dp, bottom = 54.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(items = words) { item ->
                WordItem(wordTranslation = item.second)
            }
        }
        Button(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(4.dp),
            onClick = onLearnClick,
            shape = CircleShape
        ) {
            Text(text = "Learn") // TODO strings
        }
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun QuizDetailsPreview() {
    PreviewContainer {
        QuizDetails(
            WordTranslationModel.mockAnimals
                .mapIndexed { index, wordTranslationModel ->
                    index.toString() to wordTranslationModel.toWordTranslationOrEmpty()
                }
        ) { }
    }
}