package com.ada.account

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.ada.common.OnClick
import com.ada.common.OnClickTakes
import com.ada.data.model.UserModel
import com.ada.domain.mapper.toUserOrNull
import com.ada.domain.model.User
import com.ada.ui.PreviewContainer
import com.ada.ui.PreviewDuo
import com.ada.ui.components.BottomSheetContainer
import com.ada.ui.components.SimpleButton

@Composable
fun NameBottomSheet(
    user: User?,
    onCloseClick: OnClick,
    onConfirmNameClick: OnClickTakes<String>
) {
    var name by remember {
        mutableStateOf("")
    }

    LaunchedEffect(key1 = user?.name, block = {
        user?.name?.let {
            name = it
        }
    })

    BottomSheetContainer(title = "Update name", onCloseClick = onCloseClick) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(text = "User name") },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = { onConfirmNameClick(name) },
                    )
                )

                SimpleButton(onClick = { onConfirmNameClick(name) }) {
                    Icon(imageVector = Icons.Rounded.Check, contentDescription = null)
                }
            }
        }
    }
}

@PreviewDuo
@Composable
private fun NameBottomSheetPreview() {
    PreviewContainer {
        NameBottomSheet(user = UserModel.mock().toUserOrNull(), onCloseClick = {}, onConfirmNameClick = {})
    }
}