package com.ada.signin

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ada.common.OnClick
import com.ada.common.OnClickTakes
import com.ada.common.SimpleNavigation
import com.ada.domain.model.Credentials
import com.ada.ui.PreviewContainer
import com.ada.ui.PreviewDuo
import com.ada.ui.components.LoginFields
import com.ada.ui.components.SimpleButton
import com.ada.ui.components.TopBar
import com.ada.ui.theme.bottomSheetShape
import com.ada.ui.theme.topBarTitle
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SignInScreen(
    closeScreen: SimpleNavigation,
    openQuizList: SimpleNavigation
) {
    val viewModel = hiltViewModel<SignInViewModel>()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()


    BottomSheetScaffold(
        sheetContent = {
            ResetPasswordBottomSheet(
                onCloseClick = {
                    scope.launch {
                        scaffoldState.bottomSheetState.collapse()
                    }
                },
                onSendClick = {
                    viewModel.resetPassword(
                        email = it,
                        onSuccess = {
                            Toast.makeText(
                                context,
                                "Email send.", // TODO: strings
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                        onFailure = {
                            Toast.makeText(
                                context,
                                "Something went wrong.", // TODO: strings
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    )
                }
            )
        },
        scaffoldState = scaffoldState,
        sheetShape = MaterialTheme.shapes.bottomSheetShape,
        sheetPeekHeight = 0.dp,
        content = {
            SignIn(
                onBackClick = closeScreen,
                onRegisterClick = {
                    viewModel.register(it)
                },
                onLogInClick = {
                    viewModel.login(
                        credentials = it,
                        onSuccess = {
                            openQuizList()
                        },
                        onFailure = {
                            Toast.makeText(
                                context,
                                "Invalid login or password.", // TODO: strings
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    )
                },
                onForgotPasswordClick = {
                    scope.launch {
                        scaffoldState.bottomSheetState.expand()
                    }
                }
            )
        }
    )
}

@Composable
private fun SignIn(
    onBackClick: OnClick,
    onRegisterClick: OnClickTakes<Credentials>,
    onLogInClick: OnClickTakes<Credentials>,
    onForgotPasswordClick: OnClick
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
            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.BottomCenter) {
                LoginFields(onSignUpClick = onRegisterClick, onSignInClick = onLogInClick)
            }

            Box(modifier = Modifier.weight(0.6f), contentAlignment = Alignment.BottomCenter) {
                SimpleButton(
                    onClick = onForgotPasswordClick,
                    elevation = ButtonDefaults.elevation(defaultElevation = 0.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)
                ) {
                    Text(text = "Forgot password")
                }

            }
        }
    }
}


@PreviewDuo
@Composable
private fun SignInPreview() {
    PreviewContainer {
        SignIn(onBackClick = {}, onRegisterClick = {}, onLogInClick = {},
            onForgotPasswordClick = {})
    }
}