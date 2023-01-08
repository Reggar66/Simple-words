package com.ada.account

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ada.common.OnClick
import com.ada.common.OnClickTakes
import com.ada.common.SimpleNavigation
import com.ada.common.debugLog
import com.ada.data.model.UserAccountType
import com.ada.data.model.UserModel
import com.ada.domain.mapper.toUserOrNull
import com.ada.domain.model.User
import com.ada.quizlist.DeleteBottomSheet
import com.ada.ui.PreviewContainer
import com.ada.ui.PreviewDuo
import com.ada.ui.components.AccountImage
import com.ada.ui.components.SimpleButton
import com.ada.ui.components.SimpleModalBottomSheetLayout
import com.ada.ui.components.TopBar
import com.ada.ui.theme.bottomSheetShape
import com.ada.ui.theme.dangerButton
import com.ada.ui.theme.topBarTitle
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AccountScreen(
    onBackClick: OnClick,
    openWelcomeScreen: SimpleNavigation,
    openSignUpScreen: SimpleNavigation,
    openChangePasswordScreen: SimpleNavigation,
    openDeleteAccountScreen: SimpleNavigation
) {
    val scope = rememberCoroutineScope()

    val viewModel = hiltViewModel<AccountViewModel>()
    val user by viewModel.user.collectAsState()
    val scaffoldState = rememberBottomSheetScaffoldState()

    var bottomSheetContentType by remember { mutableStateOf(BottomSheetContentType.NameChange) }

    val modalSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

    SimpleModalBottomSheetLayout(
        sheetContent = {
            DeleteBottomSheet(
                message = "Are you sure you want to delete your account? This process can not be reversed.",
                onYesClick = {
                    scope.launch {
                        modalSheetState.hide()
                        openDeleteAccountScreen()
                    }
                },
                onNoClick = {
                    scope.launch {
                        modalSheetState.hide()
                    }
                }
            )
        },
        sheetState = modalSheetState
    ) {
        BottomSheetScaffold(
            sheetContent = {
                BottomSheetContent(
                    user = user,
                    contentType = bottomSheetContentType,
                    onCloseClick = {
                        scope.launch {
                            scaffoldState.bottomSheetState.collapse()
                        }
                    },
                    onIconClick = {
                        viewModel.updateIcon(it)
                    },
                    onConfirmNameClick = {
                        viewModel.updateName(it)
                    }
                )
            },
            scaffoldState = scaffoldState,
            sheetShape = MaterialTheme.shapes.bottomSheetShape,
            sheetPeekHeight = 0.dp,
            content = {
                Account(
                    user = user,
                    onBackArrowClick = onBackClick,
                    onSignOutClick = {
                        viewModel.signOut()
                        openWelcomeScreen()
                    },
                    onSignUpClick = openSignUpScreen,
                    onPictureClick = {
                        bottomSheetContentType = BottomSheetContentType.Icons
                        scope.launch {
                            scaffoldState.bottomSheetState.expand()
                        }
                    },
                    onNameClick = {
                        bottomSheetContentType = BottomSheetContentType.NameChange
                        scope.launch {
                            scaffoldState.bottomSheetState.expand()
                        }
                    },
                    onChangePasswordClick = openChangePasswordScreen,
                    onDeleteAccountClick = {
                        scope.launch {
                            modalSheetState.show()
                        }
                    }
                )
            }
        )
    }
}

@Composable
private fun Account(
    user: User?,
    onBackArrowClick: OnClick = {},
    onSignOutClick: OnClick = {},
    onSignUpClick: OnClick = {},
    onPictureClick: OnClick = {},
    onNameClick: OnClick = {},
    onChangePasswordClick: OnClick = {},
    onDeleteAccountClick: OnClick = {}
) {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {

        TopBar(onBackArrowClick = { onBackArrowClick() }) {
            Text(
                text = "Account", // TODO: strings.xml
                style = MaterialTheme.typography.topBarTitle
            )
        }

        Top(
            modifier = Modifier.weight(1f),
            user = user,
            onPictureClick = onPictureClick,
            onNameClick = onNameClick
        )

        Bottom(
            modifier = Modifier.weight(1f),
            onSignOutClick = onSignOutClick,
            onSignUpClick = onSignUpClick,
            user = user,
            onChangePasswordClick = onChangePasswordClick,
            onDeleteAccountClick = onDeleteAccountClick
        )
    }
}

private enum class BottomSheetContentType {
    Icons,
    NameChange
}

@Composable
private fun BottomSheetContent(
    user: User?,
    contentType: BottomSheetContentType,
    onCloseClick: OnClick,
    onIconClick: OnClickTakes<String>,
    onConfirmNameClick: OnClickTakes<String>
) {
    when (contentType) {
        BottomSheetContentType.Icons -> {
            IconsBottomSheet(
                onIconClick = onIconClick,
                onCloseClick = onCloseClick
            )
        }
        BottomSheetContentType.NameChange -> {
            NameBottomSheet(
                user = user,
                onCloseClick = onCloseClick,
                onConfirmNameClick = onConfirmNameClick
            )
        }
    }
}

@Composable
private fun Top(
    modifier: Modifier = Modifier,
    user: User?,
    onPictureClick: OnClick,
    onNameClick: OnClick
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        AccountImage(
            modifier = Modifier.size(130.dp),
            picture = user?.picture,
            emoji = user?.emojiIcon,
            onPictureClick = onPictureClick
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            modifier = Modifier.clickable { onNameClick() },
            text = user?.name ?: "",
            style = MaterialTheme.typography.h4
        )
    }
}

@Composable
private fun Bottom(
    modifier: Modifier = Modifier,
    user: User?,
    onSignOutClick: OnClick,
    onSignUpClick: OnClick,
    onChangePasswordClick: OnClick,
    onDeleteAccountClick: OnClick
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        if (user?.id?.userAccountType == UserAccountType.Anonymous)
            SimpleButton(onClick = onSignUpClick) {
                Text(text = "Sign up") // TODO: strings.xml
            }

        if (user?.id?.userAccountType == UserAccountType.Permanent) {
            SimpleButton(onClick = onChangePasswordClick, shape = CircleShape) {
                Text(text = "Change password") // TODO: strings.xml
            }

            SimpleButton(onClick = onSignOutClick, shape = CircleShape) {
                Text(text = "Sign out") // TODO: strings.xml
            }

            Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.BottomCenter) {
                Column {
                    SimpleButton(
                        onClick = onDeleteAccountClick,
                        shape = CircleShape,
                        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.dangerButton)
                    ) {
                        Text(text = "Delete account") // TODO: strings.xml
                    }
                }
            }
        }
    }
}

@PreviewDuo
@Composable
private fun AccountPreview() {
    PreviewContainer {
        Account(user = UserModel.mock().toUserOrNull())
    }
}

@PreviewDuo
@Composable
private fun AccountPreview2() {
    PreviewContainer {
        Account(
            user = UserModel.mock().copy(accountType = UserAccountType.Permanent).toUserOrNull()
        )
    }
}