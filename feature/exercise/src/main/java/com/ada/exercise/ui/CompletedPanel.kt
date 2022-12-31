package com.ada.exercise.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ada.common.OnClick
import com.ada.ui.PreviewContainer
import com.ada.ui.PreviewDuo
import com.ada.ui.theme.itemBackground

@Composable
internal fun CompletedPanel(onReturnClick: OnClick) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.weight(1f)) {
            Card(
                modifier = Modifier.align(Alignment.Center),
                backgroundColor = MaterialTheme.colors.itemBackground
            ) {
                Text(
                    modifier = Modifier.padding(32.dp),
                    text = "Exercise completed, \ncongratulations!",
                    textAlign = TextAlign.Center
                )
            }
        }

        Button(onClick = onReturnClick, shape = CircleShape) {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = "Return" // TODO: strings.xml
            )
        }
    }
}

@PreviewDuo
@Composable
private fun CompletedPanelPreview() {
    PreviewContainer {
        CompletedPanel() {}
    }
}