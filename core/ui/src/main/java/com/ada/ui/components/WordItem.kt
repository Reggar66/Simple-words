package com.ada.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ada.ui.PreviewContainer
import com.ada.ui.PreviewDuo
import com.ada.ui.theme.itemBackground

@Composable
fun WordItem(leftText: String, rightText: String) {
    Card(
        Modifier.height(IntrinsicSize.Min),
        elevation = 0.dp,
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.12f)
        ),
        backgroundColor = MaterialTheme.colors.itemBackground
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(modifier = Modifier.weight(1f), text = leftText)
            Divider(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp)
            )
            Text(
                modifier = Modifier.weight(1f),
                text = rightText,
                textAlign = TextAlign.End
            )
        }
    }
}

@PreviewDuo
@Composable
private fun WordItemPreview() {
    PreviewContainer {
        WordItem(leftText = "Pies", rightText = "Dog")
    }
}


