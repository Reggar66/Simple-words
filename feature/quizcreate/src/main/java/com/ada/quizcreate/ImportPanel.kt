package com.ada.quizcreate

import androidx.compose.foundation.layout.*
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ada.common.OnClick
import com.ada.common.OnClickTakes
import com.ada.ui.PreviewContainer
import com.ada.ui.PreviewDuo
import com.ada.ui.components.SimpleButton

@Composable
fun ImportPanel(onCancelClick: OnClick, onImportClick: OnClickTakes<String>) {
    var import by remember {
        mutableStateOf(exampleImportString())
    }
    Column(modifier = Modifier.padding(8.dp)) {
        OutlinedTextField(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            value = import,
            onValueChange = { import = it })

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            SimpleButton(onClick = onCancelClick) {
                Text(text = "Cancel")
            }
            SimpleButton(onClick = { import = "" }) {
                Text(text = "Clear")
            }
            SimpleButton(onClick = { onImportClick(import) }) {
                Text(text = "Import")
            }
        }
    }
}

private fun exampleImportString() = "word1;translation1\nword2;translation2\nword3;translation3"

@PreviewDuo
@Composable
private fun ImportPanelPreview() {
    PreviewContainer {
        ImportPanel(onCancelClick = {}, onImportClick = {})
    }
}