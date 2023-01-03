package com.ada.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ada.common.OnClick
import com.ada.common.R
import com.ada.common.extensions.toSp
import com.ada.data.model.UserModel
import com.ada.domain.mapper.toUserOrNull
import com.ada.ui.PreviewContainer

/* == Disclaimer about preview ==
   Emoji icon shows in interaction mode in preview since its size is calculated during composition.
   Other wise it won't show up in preview.
*/

@Composable
fun AccountImage(
    modifier: Modifier = Modifier,
    picture: String?,
    emoji: String?,
    borderWidth: Dp = 2.dp,
    onPictureClick: OnClick? = null
) {

    var imageSizePx by remember {
        mutableStateOf(0)
    }

    val emojiSize = imageSizePx.toSp()

    Box(
        modifier = Modifier
            .onGloballyPositioned {
                imageSizePx = it.size.height / 2
            }
            .onSizeChanged {
                imageSizePx = it.height / 2
            }
            .then(modifier.aspectRatio(1f)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier
                .border(
                    width = borderWidth,
                    color = MaterialTheme.colors.onBackground,
                    shape = CircleShape
                )
                .clip(CircleShape)
                .fillMaxSize(),
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "User picture.", // TODO: strings.xml
        )

        if (onPictureClick != null)
            Box(modifier = Modifier
                .clip(CircleShape)
                .fillMaxSize()
                .clickable { onPictureClick() })

        // TODO: user settings to use user picture (custom link) or animal emoji icon.
        if (picture == null)
            Text(
                text = emoji ?: "",
                fontSize = emojiSize,//(size.value / 2).sp,
                color = Color.Black
            )
    }
}

@Preview
@Composable
private fun AccountImagePreview() {
    PreviewContainer {
        val user = UserModel.mock().toUserOrNull()
        AccountImage(
            modifier = Modifier.size(130.dp),
            picture = user?.picture,
            emoji = user?.emojiIcon,
            onPictureClick = {})
        AccountImage(
            modifier = Modifier.size(130.dp),
            picture = user?.picture,
            emoji = user?.emojiIcon
        )
    }
}