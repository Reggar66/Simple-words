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
import com.ada.ui.PreviewContainer
import com.ada.ui.PreviewDuo
import com.ada.ui.theme.itemBackground

@Composable
fun AccountBar(name: String, onPictureClick: OnClick) {
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
            Image(
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colors.onBackground,
                        shape = CircleShape
                    )
                    .height(IntrinsicSize.Max)
                    .aspectRatio(1f)
                    .clip(CircleShape)
                    .clickable { onPictureClick() },
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = null
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = name,
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
        AccountBar(name = "CoolMailButVeryLooooooooooooooong@mail.com", onPictureClick = {})
    }
}