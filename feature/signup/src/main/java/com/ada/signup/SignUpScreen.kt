package com.ada.signup

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.ada.common.OnClick
import com.ada.common.OnClickTakes
import com.ada.common.SimpleNavigation
import com.ada.data.model.UserAccountType
import com.ada.domain.model.Credentials
import com.ada.ui.PreviewContainer
import com.ada.ui.PreviewDuo
import com.ada.ui.components.LoginFields
import com.ada.ui.components.TopBar
import com.ada.ui.theme.topBarTitle

@Composable
fun SignUpScreen(closeScreen: SimpleNavigation, openQuizList: SimpleNavigation) {
    val viewModel = hiltViewModel<SignUpViewModel>()
    val user by viewModel.user.collectAsState(initial = null)

    val context = LocalContext.current

    SignUp(
        onBackClick = closeScreen,
        onSignUpClick = {
            when (user?.id?.userAccountType) {
                UserAccountType.Anonymous -> viewModel.convertToPermanent(it) {
                    closeScreen()
                }
                null -> viewModel.signUp(it) {
                    openQuizList()
                }
                else -> Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        }
    )
}

@Composable
private fun SignUp(
    onBackClick: OnClick = {},
    onSignUpClick: OnClickTakes<Credentials> = {}
) {
    Column {
        TopBar(onBackArrowClick = onBackClick) {
            Text(
                text = "Sign up", // TODO: strings.xml
                style = MaterialTheme.typography.topBarTitle
            )
        }


        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoginFields(registration = true, onSignUpClick = onSignUpClick)
        }
    }
}

@PreviewDuo
@Composable
private fun SignUpScreenPreview() {
    PreviewContainer {
        SignUp()
    }
}