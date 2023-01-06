package com.ada.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ada.common.OnClickTakes
import com.ada.common.extensions.isValidEmail
import com.ada.domain.model.Credentials
import com.ada.ui.PreviewContainer
import com.ada.ui.PreviewDuo

@Composable
fun LoginFields(
    registration: Boolean = false,
    onSignUpClick: OnClickTakes<Credentials> = {},
    onLogInClick: OnClickTakes<Credentials> = {}
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmation by remember { mutableStateOf("") }

    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier.padding(8.dp)
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "Email") }, // TODO strings
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "Password") }, // TODO strings
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
            })
        )
        if (registration)
            Text(
                text = "Password must be at least 6 characters long.", // TODO: strings.xml
                fontSize = 12.sp
            )

        if (registration)
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = confirmation,
                onValueChange = { confirmation = it },
                label = { Text(text = "Confirm password") }, // TODO strings
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {
                    focusManager.clearFocus()
                })
            )


        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            if (!registration)
                SimpleButton(
                    onClick = { onLogInClick(Credentials(email = email, password = password)) },
                    enabled = email.isNotEmpty() && email.isValidEmail() && password.isNotEmpty()
                ) {
                    Text(text = "Sign in")
                }
            else
                SimpleButton(
                    onClick = {
                        onSignUpClick(
                            Credentials(
                                email = email,
                                password = password
                            )
                        )
                    },
                    enabled = email.isNotEmpty() && email.isValidEmail() && password.isNotEmpty() && password.length >= 6 && password == confirmation
                ) {
                    Text(text = "Sign up") // TODO: strings.xml
                }

        }
    }
}

@PreviewDuo
@Composable
private fun LoginFieldsPreview() {
    PreviewContainer {
        LoginFields(onSignUpClick = {}, onLogInClick = {})
    }
}

@PreviewDuo
@Composable
private fun LoginFieldsPreview2() {
    PreviewContainer {
        LoginFields(registration = true, onSignUpClick = {}, onLogInClick = {})
    }
}