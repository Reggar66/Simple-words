package com.ada.signin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import com.ada.ui.components.TopBar
import com.ada.ui.theme.topBarTitle

@Composable
fun SignInScreen(
    closeScreen: SimpleNavigation,
    openQuizList: SimpleNavigation
) {
    val viewModel = hiltViewModel<SignInViewModel>()

    SignIn(
        onBackClick = closeScreen,
        onRegisterClick = {
            viewModel.register(it)
        },
        onLogInClick = {
            viewModel.login(it) {
                openQuizList()
            }
        }
    )
}

@Composable
private fun SignIn(
    onBackClick: OnClick,
    onRegisterClick: OnClickTakes<Credentials>,
    onLogInClick: OnClickTakes<Credentials>
) {

    Column {
        TopBar(onBackArrowClick = onBackClick) {
            Text(
                text = "Sign in", // TODO: strings.xml
                style = MaterialTheme.typography.topBarTitle
            )
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoginFields(onSignUpClick = onRegisterClick, onLogInClick = onLogInClick)
        }
    }
}


@PreviewDuo
@Composable
private fun SignInPreview() {
    PreviewContainer {
        SignIn(onBackClick = {}, onRegisterClick = {}, onLogInClick = {})
    }
}