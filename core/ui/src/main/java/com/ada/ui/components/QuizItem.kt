package com.ada.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ada.common.OnClick
import com.ada.domain.model.Quiz
import com.ada.data.model.QuizModel
import com.ada.domain.mapper.toQuizOrEmpty
import com.ada.ui.PreviewContainer
import com.ada.ui.theme.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun QuizItem(modifier: Modifier = Modifier, quiz: Quiz, onClick: OnClick) {
    Card(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        elevation = 1.dp,
        border = BorderStroke(
            width = 2.dp,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.12f)
        ),
        backgroundColor = Color.Transparent
    ) {
        Box(modifier = Modifier.background(if (quiz.isComplete) MaterialTheme.colors.surface else MaterialTheme.colors.itemBackground)) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = quiz.name, style = MaterialTheme.typography.quizTitle
                )

                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                )

                Row(modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp)) {
                    TextLabel(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 8.dp),
                        label = "Words",
                        value = quiz.wordsNumber.toString()
                    )

                    TextLabel(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 8.dp),
                        horizontalAlignment = Alignment.End,
                        label = "Progress",
                        value = "${quiz.completedWords}/${quiz.wordsNumber}"
                    )
                }
            }
        }
    }
}

@Composable
private fun TextLabel(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start
) {
    Column(modifier = modifier, horizontalAlignment = horizontalAlignment) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = label, style = MaterialTheme.typography.quizLabels)
            Text(text = value)
        }
    }
}


@Preview(uiMode = UI_MODE_NIGHT_YES)
@Preview
@Composable
private fun QuizItemPreview() {
    PreviewContainer(modifier = Modifier.padding(8.dp)) {
        QuizItem(quiz = QuizModel.mockSeasons.toQuizOrEmpty()) {

        }

        Spacer(modifier = Modifier.height(16.dp))

        QuizItem(quiz = QuizModel.mockAnimalsCompleted.toQuizOrEmpty()) {

        }
    }
}