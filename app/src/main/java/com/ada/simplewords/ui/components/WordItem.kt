package com.ada.simplewords.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ada.simplewords.domain.models.WordTranslationModel

@Composable
fun WordItem(wordTranslationModel: WordTranslationModel) {
    Card {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = wordTranslationModel.word)
            Text(text = wordTranslationModel.translation)
        }
    }
}