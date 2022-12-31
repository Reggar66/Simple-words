package com.ada.exercise.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ada.common.OnClick
import com.ada.common.OnClickTakes
import com.ada.data.model.WordTranslationModel
import com.ada.domain.mapper.toWordTranslationOrEmpty
import com.ada.domain.model.WordTranslation
import com.ada.exercise.ValidationState
import com.ada.ui.PreviewContainer
import com.ada.ui.PreviewDuo
import com.ada.ui.theme.itemBackground

@Composable
internal fun AnswerPanelModern(
    modifier: Modifier = Modifier,
    validationState: ValidationState,
    items: List<WordTranslation>,
    onItemClick: OnClickTakes<WordTranslation>,
    onNextClick: OnClick
) {
    Column(modifier = Modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        LazyVerticalGrid(
            modifier = modifier,
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(
                8.dp,
                alignment = Alignment.CenterVertically
            ),
            horizontalArrangement = Arrangement.spacedBy(
                8.dp,
                alignment = Alignment.CenterHorizontally
            ),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(items = items) { item ->
                Item(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = item.translation,
                    onClick = { if (validationState == ValidationState.WAITING) onItemClick(item) }
                )
            }
        }

        Box(modifier = Modifier.height(ButtonDefaults.MinHeight)) {
            if (validationState != ValidationState.WAITING)
                Button(onClick = { onNextClick() }, shape = CircleShape) {
                    Text(text = "Next") // TODO: strings.xml
                }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun Item(
    modifier: Modifier = Modifier,
    text: String,
    onClick: OnClick
) {
    Card(
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.12f)
        ),
        backgroundColor = Color.Transparent,
        onClick = onClick
    ) {
        Box(
            modifier = Modifier
                .background(color = MaterialTheme.colors.itemBackground)
                .then(modifier),
            contentAlignment = Alignment.Center
        ) {
            Text(modifier = Modifier.padding(16.dp), text = text)
        }
    }
}

@PreviewDuo
@Composable
private fun AnswerPanelModernPreview() {
    PreviewContainer {
        AnswerPanelModern(
            validationState = ValidationState.WAITING,
            items = WordTranslationModel.mockAnimals.map { it.toWordTranslationOrEmpty() },
            onItemClick = {},
            onNextClick = {},
        )
    }
}

@PreviewDuo
@Composable
private fun AnswerPanelModernPreview2() {
    PreviewContainer {
        AnswerPanelModern(
            validationState = ValidationState.CORRECT,
            items = WordTranslationModel.mockAnimals.map { it.toWordTranslationOrEmpty() },
            onItemClick = {},
            onNextClick = {},
        )
    }
}