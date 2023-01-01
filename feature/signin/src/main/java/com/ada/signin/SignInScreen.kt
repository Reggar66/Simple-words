package com.ada.signin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ada.common.OnClick
import com.ada.common.OnClickTakes
import com.ada.common.SimpleNavigation
import com.ada.ui.PreviewContainer
import com.ada.ui.PreviewDuo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun SignInScreen(openQuizList: SimpleNavigation) {
    val viewModel = hiltViewModel<SignInViewModel>()

    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = null, block = {
        if (viewModel.isSignedIn())
            openQuizList()
    })

    if (!viewModel.isSignedIn())
        SignIn(
            onRegisterClick = {
                viewModel.register(it)
            },
            onLogInClick = {
                viewModel.login(it) {
                    openQuizList()
                }
            },
            onLogOutClick = { viewModel.signOut() },
            onAnonymousClick = {
                viewModel.signInAnon {
                    openQuizList()
                }
            }
        )
}

@Composable
private fun SignIn(
    onRegisterClick: OnClickTakes<Credentials>,
    onLogInClick: OnClickTakes<Credentials>,
    onLogOutClick: OnClick,
    onAnonymousClick: OnClick
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = onAnonymousClick) {
            Text(text = "Anonymous")
        }

        LoginFields(onRegisterClick = onRegisterClick, onLogInClick = onLogInClick)

        Button(onClick = onLogOutClick) {
            Text(text = "Log out")
        }
    }
}

@Composable
private fun LoginFields(
    onRegisterClick: OnClickTakes<Credentials>,
    onLogInClick: OnClickTakes<Credentials>
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val focusManager = LocalFocusManager.current

    Column {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "Email") }, // TODO strings
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
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


        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(
                onClick = { onLogInClick(Credentials(email = email, password = password)) },
                enabled = email.isNotEmpty() || password.isNotEmpty()
            ) {
                Text(text = "Log In")
            }

            Button(
                onClick = { onRegisterClick(Credentials(email = email, password = password)) },
                enabled = email.isNotEmpty() || password.isNotEmpty()
            ) {
                Text(text = "Register")
            }
        }
    }
}

@PreviewDuo
@Composable
private fun SignInPreview() {
    PreviewContainer {
        SignIn(onRegisterClick = {}, onLogInClick = {}, onAnonymousClick = {}, onLogOutClick = {})
    }
}