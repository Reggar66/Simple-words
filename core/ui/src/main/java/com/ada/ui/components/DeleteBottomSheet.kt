package com.ada.quizlist

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ada.common.OnClick
import com.ada.ui.PreviewContainer
import com.ada.ui.PreviewDuo
import com.ada.ui.components.SimpleButton
import com.ada.ui.theme.border
import com.ada.ui.theme.dangerButton

@Composable
fun DeleteBottomSheet(message: String, onYesClick: OnClick, onNoClick: OnClick) {
    Box(modifier = Modifier.background(color = MaterialTheme.colors.surface)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                modifier = Modifier.padding(bottom = 16.dp),
                text = message
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                // TODO: strings
                ModalSheetButton(
                    text = "Yes",
                    textColor = MaterialTheme.colors.dangerButton,
                    onClick = onYesClick
                )
                // TODO: strings
                ModalSheetButton(
                    text = "No",
                    onClick = onNoClick
                )
            }
        }
    }
}

@Composable
private fun ModalSheetButton(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color = MaterialTheme.colors.onSurface,
    onClick: OnClick
) {
    Column(
        modifier = modifier
            .border(
                width = 1.dp,
                color = MaterialTheme.colors.border,
                shape = MaterialTheme.shapes.medium
            )
            .clip(MaterialTheme.shapes.medium)
            .clickable { onClick() }
            .fillMaxWidth()
            .padding(18.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = text, color = textColor, fontWeight = FontWeight.SemiBold)
    }
}

@PreviewDuo
@Composable
private fun DeleteBottomSheetPreview() {
    PreviewContainer {
        DeleteBottomSheet(message = "", onYesClick = {}, onNoClick = {})
    }
}