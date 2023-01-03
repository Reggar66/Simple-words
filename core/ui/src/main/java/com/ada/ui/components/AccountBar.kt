package com.ada.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.ada.common.OnClick
import com.ada.common.R
import com.ada.data.model.UserModel
import com.ada.domain.mapper.toUserOrNull
import com.ada.domain.model.User
import com.ada.ui.PreviewContainer
import com.ada.ui.PreviewDuo
import com.ada.ui.theme.itemBackground

@Composable
fun AccountBar(user: User?, onPictureClick: OnClick) {
    Surface(
        color = Color.Transparent,
        elevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colors.itemBackground)
                .height(56.dp)
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AccountImage(
                modifier = Modifier.height(IntrinsicSize.Max),
                picture = user?.picture,
                emoji = user?.emojiIcon,
                borderWidth = 1.dp,
                onPictureClick = onPictureClick
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = user?.name ?: "",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.h6
            )
        }
    }
}

@PreviewDuo
@Composable
private fun TopBarPreview() {
    PreviewContainer {
        AccountBar(user = UserModel.mock().toUserOrNull(), onPictureClick = {})
    }
}