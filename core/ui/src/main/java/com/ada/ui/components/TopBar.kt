package com.ada.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.Indication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ada.common.OnClick
import com.ada.common.R
import com.ada.ui.PreviewContainer
import com.ada.ui.PreviewDuo
import com.ada.ui.theme.itemBackground

@Composable
fun TopBar(
    backArrowVisible: Boolean = true,
    onBackArrowClick: OnClick = {},
    content: @Composable BoxScope.() -> Unit
) {
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
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (backArrowVisible)
                Image(
                    modifier = Modifier
                        .height(24.dp)
                        .aspectRatio(1f)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(bounded = false),
                            onClick = { onBackArrowClick() }),
                    painter = painterResource(id = R.drawable.ic_round_arrow_back_24),
                    contentDescription = "Return to previous screen", // TODO: strings.xml
                    colorFilter = ColorFilter.tint(color = MaterialTheme.colors.onBackground)
                )
            else
                Spacer(
                    modifier = Modifier
                        .height(24.dp)
                        .aspectRatio(1f)
                )

            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center,
                content = content
            )

            Spacer(
                modifier = Modifier
                    .height(24.dp)
                    .aspectRatio(1f)
            )
        }
    }
}

@PreviewDuo
@Composable
private fun TopBarPreview() {
    PreviewContainer {
        TopBar {
            Text(text = "Some example title content")
        }
    }
}

@PreviewDuo
@Composable
private fun TopBar2Preview() {
    PreviewContainer {
        TopBar(backArrowVisible = false) {
            Text(text = "Some example title content")
        }
    }
}