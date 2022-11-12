@file:OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)

package com.example.simplewords.feature.list

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.simplewords.common.OnClick
import com.example.simplewords.common.OnClickType
import com.example.simplewords.data.QuizItemData
import com.example.simplewords.data.WordTranslation
import com.example.simplewords.feature.details.QuizDetailsScreen
import com.example.simplewords.ui.components.PreviewContainer
import com.example.simplewords.ui.components.QuizItem
import com.example.simplewords.ui.components.WordItem
import com.example.simplewords.ui.navigation.SimpleNavigationTakes
import kotlinx.coroutines.launch

@Composable
fun QuizListScreen(openQuiz: SimpleNavigationTakes<QuizData>) {
    val viewModel = viewModel<QuizListViewModel>()
    val state = viewModel.quizListState


    QuizListImpl(
        quizListState = state,
        onItemClick = { openQuiz(it) },
        onSortClick = { viewModel.sortByName() },
        onShuffleClick = { viewModel.shuffle() })
}

@Composable
private fun QuizListImpl(
    quizListState: QuizListScreenState,
    onItemClick: OnClickType<QuizData>,
    onSortClick: OnClick,
    onShuffleClick: OnClick
) {
    val scaffoldState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()

    var selectedQuizId by remember {
        mutableStateOf(-1)
    }

    BottomSheetScaffold(
        sheetContent = { BottomSheetContent(quizId = selectedQuizId) },
        scaffoldState = scaffoldState,
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        sheetPeekHeight = 0.dp,
        content = {
            Quizzes(
                quizData = quizListState.quizzes,
                onItemCLick = {
                    scope.launch {
                        if (scaffoldState.bottomSheetState.isExpanded)
                            scaffoldState.bottomSheetState.collapse()
                        selectedQuizId = it.quizItem.id
                        scaffoldState.bottomSheetState.expand()
                    }
                },
                onSortClick = onSortClick,
                onShuffleClick = onShuffleClick
            )
        })
}

@Composable
private fun BottomSheetContent(quizId: Int) {
    if (quizId == -1)
        return
    Column(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.height(8.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
                .padding(horizontal = 100.dp),
            shape = CircleShape
        ) {}
        QuizDetailsScreen(quizId = quizId)
    }
}

@Composable
private fun Quizzes(
    quizData: List<QuizData>,
    onItemCLick: OnClickType<QuizData>,
    onSortClick: OnClick,
    onShuffleClick: OnClick
) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            contentPadding = PaddingValues(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(items = quizData, key = { it.quizItem.id }) { itemQuizData ->
                QuizItem(
                    modifier = Modifier
                        .animateItemPlacement(),
                    quizData = itemQuizData,
                    onClick = { onItemCLick(itemQuizData) })
            }
        }

        // TODO remove buttons
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(onClick = onSortClick) {
                Text(text = "Sort")
            }
            Button(onClick = onShuffleClick) {
                Text(text = "Shuffle")
            }
        }
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun QuizListPreview() {
    PreviewContainer {
        Quizzes(quizData = QuizData.mock, onItemCLick = {}, {}) {}
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun BottomSheetPreview() {
    PreviewContainer {
        BottomSheetContent(quizId = 1)
    }
}