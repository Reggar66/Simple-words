package com.ada.welcome

import androidx.compose.foundation.layout.*
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ada.common.OnClick
import com.ada.common.SimpleNavigation
import com.ada.common.UserNameGenerator
import com.ada.ui.PreviewContainer
import com.ada.ui.PreviewDuo
import com.ada.ui.components.SimpleButton

@Composable
fun WelcomeScreen(
    openSignIn: SimpleNavigation,
    openSignUp: SimpleNavigation,
    openQuizList: SimpleNavigation
) {
    val viewModel = hiltViewModel<WelcomeViewModel>()

    LaunchedEffect(key1 = null, block = {
        if (viewModel.isSignedIn())
            openQuizList()
    })


    if (!viewModel.isSignedIn())
        Welcome(
            onSignInClick = { openSignIn() },
            onSignUpClick = { openSignUp() },
            onAnonymousClick = {
                viewModel.signInAnon {
                    openQuizList()
                }
            }
        )
}

@Composable
private fun Welcome(
    onSignInClick: OnClick,
    onSignUpClick: OnClick,
    onAnonymousClick: OnClick
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
            // TODO: some app logo
            Text(
                text = remember { UserNameGenerator.randomNameWithIcon().emoji },
                fontSize = 100.sp,
                color = Color.Black
            )
        }

        SimpleButton(onClick = onSignInClick) {
            Text(text = "Sign in") // TODO: strings
        }

        SimpleButton(onClick = onSignUpClick) {
            Text(text = "Sign up") // TODO: strings
        }

        SimpleButton(
            onClick = onAnonymousClick,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.background,
                contentColor = MaterialTheme.colors.onBackground,
                disabledBackgroundColor = Color.Transparent,
                disabledContentColor = Color.DarkGray
            )
        ) {
            Text(text = "Continue without account") // TODO: strings
        }
    }
}

@PreviewDuo
@Composable
private fun WelcomePreview() {
    PreviewContainer {
        Welcome(onSignInClick = {}, onSignUpClick = {}, onAnonymousClick = {})
    }
}