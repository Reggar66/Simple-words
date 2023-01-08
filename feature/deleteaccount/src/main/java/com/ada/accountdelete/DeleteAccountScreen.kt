package com.ada.accountdelete

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ada.common.OnClick
import com.ada.common.OnClickTakes
import com.ada.common.SimpleNavigation
import com.ada.domain.model.Credentials
import com.ada.ui.PreviewContainer
import com.ada.ui.PreviewDuo
import com.ada.ui.components.LoginFields
import com.ada.ui.components.TopBar
import com.ada.ui.theme.dangerButton
import com.ada.ui.theme.topBarTitle

@Composable
fun DeleteAccountScreen(closeScreen: SimpleNavigation, openWelcomeScreen: SimpleNavigation) {
    val vieModel = hiltViewModel<DeleteAccountViewModel>()

    DeleteAccount(
        onBackArrowClick = { closeScreen() },
        onSignInClick = {
            vieModel.removeAccountWithData(it, onSuccess = {
                openWelcomeScreen()
            })
        }
    )
}

@Composable
private fun DeleteAccount(onBackArrowClick: OnClick, onSignInClick: OnClickTakes<Credentials>) {
    Column(modifier = Modifier.fillMaxSize()) {
        TopBar(onBackArrowClick = onBackArrowClick) {
            Text(text = "Delete account", style = MaterialTheme.typography.topBarTitle)
        }

        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.Center) {
            Text(modifier = Modifier.padding(8.dp), text = "Verify your identity.")
            LoginFields(
                onSignInClick = onSignInClick,
                signInButtonText = "Delete account",
                signInButtonColors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.dangerButton)
            )
        }
    }
}

@PreviewDuo
@Composable
private fun DeleteAccountPreview() {
    PreviewContainer {
        DeleteAccount(onBackArrowClick = { }, onSignInClick = {})
    }
}