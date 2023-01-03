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

@Composable
fun AccountScreen(onBackClick: OnClick, openSignInScreen: SimpleNavigation) {

    val viewModel = hiltViewModel<AccountViewModel>()
    val user by viewModel.user.collectAsState(initial = null)

    Account(
        user = user,
        onBackArrowClick = onBackClick,
        onSignOutClick = {
            viewModel.signOut()
            openSignInScreen()
        },
        onRegisterClick = {/*TODO*/ })
}

@Composable
private fun Account(
    user: User?,
    onBackArrowClick: OnClick = {},
    onSignOutClick: OnClick = {},
    onRegisterClick: OnClick = {}
) {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {

        TopBar(onBackArrowClick = { onBackArrowClick() }) {
            Text(text = "Account", style = MaterialTheme.typography.h6) // TODO: strings.xml
        }

        Top(modifier = Modifier.weight(1f), user = user)

        Bottom(
            modifier = Modifier.weight(1f),
            onSignOutClick = onSignOutClick,
            onRegisterClick = onRegisterClick,
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
    onRegisterClick: OnClick
) {
    Column(modifier = modifier) {
        if (user?.accountType == UserAccountType.Anonymous)
            SimpleButton(onClick = onRegisterClick) {
                Text(text = "Register") // TODO: strings.xml
            }

        if (user?.accountType == UserAccountType.Permanent)
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