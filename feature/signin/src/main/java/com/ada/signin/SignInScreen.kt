package com.ada.signin

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.ada.common.OnClick
import com.ada.common.OnClickTakes
import com.ada.common.SimpleNavigation
import com.ada.domain.model.Credentials
import com.ada.ui.PreviewContainer
import com.ada.ui.PreviewDuo
import com.ada.ui.components.LoginFields

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



@PreviewDuo
@Composable
private fun SignInPreview() {
    PreviewContainer {
        SignIn(onRegisterClick = {}, onLogInClick = {}, onAnonymousClick = {}, onLogOutClick = {})
    }
}