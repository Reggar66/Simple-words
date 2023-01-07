package com.ada.signin

import androidx.compose.foundation.layout.*
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ada.common.OnClick
import com.ada.common.OnClickTakes
import com.ada.common.extensions.isValidEmail
import com.ada.ui.PreviewContainer
import com.ada.ui.PreviewDuo
import com.ada.ui.components.BottomSheetContainer
import com.ada.ui.components.SimpleButton

@Composable
fun ResetPasswordBottomSheet(onCloseClick: OnClick, onSendClick: OnClickTakes<String>) {
    var email by remember {
        mutableStateOf("")
    }
    BottomSheetContainer(title = "Password reset", onCloseClick = onCloseClick) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
        ) {

            Text(text = "Enter email address on which we will send password reset instructions")

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = email,
                onValueChange = { email = it },
                label = { Text(text = "Email") }
            )

            SimpleButton(onClick = { onSendClick(email) }, enabled = email.isValidEmail()) {
                Text(text = "Send")
            }
        }
    }
}

@PreviewDuo
@Composable
private fun ResetPasswordBottomSheetPreview() {
    PreviewContainer {
        ResetPasswordBottomSheet(onCloseClick = {}, onSendClick = {})
    }
}