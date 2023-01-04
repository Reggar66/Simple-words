package com.ada.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ada.ui.PreviewContainer
import com.ada.ui.PreviewDuo
import com.ada.ui.components.LoginFields

@Composable
fun SignUpScreen() {
    // TODO: ViewModel end registration stuff.
    SignUp()
}

@Composable
private fun SignUp() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LoginFields(registration = true, onRegisterClick = {}, onLogInClick = {})
    }
}

@PreviewDuo
@Composable
private fun SignUpScreenPreview() {
    PreviewContainer {
        SignUp()
    }
}