package com.ada.account

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ada.common.OnClick
import com.ada.common.SimpleNavigation
import com.ada.data.model.UserAccountType
import com.ada.data.model.UserModel
import com.ada.domain.mapper.toUserOrNull
import com.ada.domain.model.User
import com.ada.ui.PreviewContainer
import com.ada.ui.PreviewDuo
import com.ada.ui.components.AccountImage
import com.ada.ui.components.SimpleButton
import com.ada.ui.components.TopBar
import com.ada.ui.theme.bottomSheetShape
import com.ada.ui.theme.topBarTitle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AccountScreen(
    onBackClick: OnClick,
    openWelcomeScreen: SimpleNavigation,
    openSignUpScreen: SimpleNavigation
) {
    val scope = rememberCoroutineScope()

    val viewModel = hiltViewModel<AccountViewModel>()
    val user by viewModel.user.collectAsState()
    val scaffoldState = rememberBottomSheetScaffoldState()


    BottomSheetScaffold(
        sheetContent = {
            IconsBottomSheet(
                onIconClick = {
                    viewModel.updateIcon(it)
                },
                onCloseClick = {
                    scope.launch {
                        scaffoldState.bottomSheetState.collapse()
                    }
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
                    scope.launch {
                        scaffoldState.bottomSheetState.expand()
                    }
                }
            )
        }
    )
}

@Composable
private fun Account(
    user: User?,
    onBackArrowClick: OnClick = {},
    onSignOutClick: OnClick = {},
    onSignUpClick: OnClick = {},
    onPictureClick: OnClick = {}
) {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {

        TopBar(onBackArrowClick = { onBackArrowClick() }) {
            Text(
                text = "Account",
                style = MaterialTheme.typography.topBarTitle
            ) // TODO: strings.xml
        }

        Top(modifier = Modifier.weight(1f), user = user, onPictureClick = onPictureClick)

        Bottom(
            modifier = Modifier.weight(1f),
            onSignOutClick = onSignOutClick,
            onSignUpClick = onSignUpClick,
            user = user
        )
    }
}

@Composable
private fun Top(
    modifier: Modifier = Modifier,
    user: User?,
    onPictureClick: OnClick
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

        Text(text = user?.name ?: "", style = MaterialTheme.typography.h4)
    }
}

@Composable
private fun Bottom(
    modifier: Modifier = Modifier,
    user: User?,
    onSignOutClick: OnClick,
    onSignUpClick: OnClick
) {
    Column(modifier = modifier) {
        if (user?.id?.userAccountType == UserAccountType.Anonymous)
            SimpleButton(onClick = onSignUpClick) {
                Text(text = "Sign up") // TODO: strings.xml
            }

        if (user?.id?.userAccountType == UserAccountType.Permanent)
            SimpleButton(onClick = onSignOutClick, shape = CircleShape) {
                Text(text = "Sign out") // TODO: strings.xml
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