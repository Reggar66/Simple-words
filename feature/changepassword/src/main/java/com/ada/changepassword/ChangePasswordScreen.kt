package com.ada.changepassword

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ada.common.OnClick
import com.ada.common.OnClickTakes
import com.ada.common.SimpleNavigation
import com.ada.domain.model.Credentials
import com.ada.ui.PreviewContainer
import com.ada.ui.PreviewDuo
import com.ada.ui.components.LoginFields
import com.ada.ui.components.SimpleButton

@Composable
fun ChangePasswordScreen(closeScreen: SimpleNavigation) {
    val viewModel = hiltViewModel<ChangePasswordViewModel>()
    val isAuthenticated by viewModel.isAuthenticated.collectAsState()
    val isUpdated by viewModel.isUpdated.collectAsState()

    LaunchedEffect(key1 = isUpdated, block = {
        if (isUpdated) {
            closeScreen()
        }
    })

    ChangePassword(
        reAuthenticated = isAuthenticated,
        onSignInClick = {
            viewModel.reAuthenticate(it)
        },
        onUpdateClick = {
            viewModel.updatePassword(it.password)
        }
    )
}

@Composable
private fun ChangePassword(
    reAuthenticated: Boolean = false,
    onSignInClick: OnClickTakes<Credentials>,
    onUpdateClick: OnClickTakes<Credentials>
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (!reAuthenticated) {
            Text(text = "Confirm your identity")
            LoginFields(onSignInClick = onSignInClick)
        } else
            UpdateFields(onUpdateClick = onUpdateClick)
    }
}

@Composable
private fun UpdateFields(onUpdateClick: OnClickTakes<Credentials>) {
    var password by remember { mutableStateOf("") }
    var confirmation by remember { mutableStateOf("") }

    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier.padding(8.dp)
    ) {
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
            }),
            visualTransformation = PasswordVisualTransformation()
        )
        Text(
            text = "Password must be at least 6 characters long.", // TODO: strings.xml
            fontSize = 12.sp
        )

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
            }),
            visualTransformation = PasswordVisualTransformation()
        )


        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            SimpleButton(
                onClick = {
                    onUpdateClick(Credentials(email = "", password = password))
                },
                enabled = password.isNotEmpty() && password.length >= 6 && password == confirmation
            ) {
                Text(text = "Change password") // TODO: strings.xml
            }
        }
    }
}

@PreviewDuo
@Composable
private fun ChangePasswordPreview() {
    PreviewContainer {
        ChangePassword(reAuthenticated = false, onSignInClick = {}, onUpdateClick = {})
    }
}

@PreviewDuo
@Composable
private fun ChangePasswordPreview2() {
    PreviewContainer {
        ChangePassword(reAuthenticated = true, onSignInClick = {}, onUpdateClick = {})
    }
}