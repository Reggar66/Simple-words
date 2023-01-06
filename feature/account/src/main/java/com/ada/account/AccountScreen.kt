package com.ada.account

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.ada.ui.theme.topBarTitle

@Composable
fun AccountScreen(
    onBackClick: OnClick,
    openWelcomeScreen: SimpleNavigation,
    openSignUpScreen: SimpleNavigation
) {

    val viewModel = hiltViewModel<AccountViewModel>()
    val user by viewModel.user.collectAsState(initial = null)

    Account(
        user = user,
        onBackArrowClick = onBackClick,
        onSignOutClick = {
            viewModel.signOut()
            openWelcomeScreen()
        },
        onSignUpClick = openSignUpScreen
    )
}

@Composable
private fun Account(
    user: User?,
    onBackArrowClick: OnClick = {},
    onSignOutClick: OnClick = {},
    onSignUpClick: OnClick = {}
) {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {

        TopBar(onBackArrowClick = { onBackArrowClick() }) {
            Text(
                text = "Account",
                style = MaterialTheme.typography.topBarTitle
            ) // TODO: strings.xml
        }

        Top(modifier = Modifier.weight(1f), user = user)

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
    user: User?
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        AccountImage(modifier = Modifier.size(130.dp),
            picture = user?.picture,
            emoji = user?.emojiIcon,
            onPictureClick = { /*TODO: way of choosing icon.*/ })

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