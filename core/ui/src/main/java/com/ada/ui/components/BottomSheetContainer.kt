package com.ada.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ada.common.OnClick
import com.ada.ui.PreviewContainer
import com.ada.ui.PreviewDuo
import com.ada.ui.theme.bottomSheetSurface

@Composable
fun BottomSheetContainer(
    title: String?,
    onCloseClick: OnClick,
    content: @Composable () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.bottomSheetSurface),
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .clickable { onCloseClick() }
                    .size(40.dp)
                    .clip(CircleShape),
                imageVector = Icons.Rounded.KeyboardArrowDown,
                contentDescription = null
            )

            Text(
                text = title ?: "",
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                fontSize = 24.sp
            )

            Spacer(modifier = Modifier.width(40.dp))
        }
        Divider(modifier = Modifier.fillMaxWidth())

        Box(modifier = Modifier.background(color = MaterialTheme.colors.bottomSheetSurface)) {
            content()
        }
    }
}

@PreviewDuo
@Composable
private fun BottomSheetPreview() {
    PreviewContainer {
        BottomSheetContainer(
            title = "Cool title",
            onCloseClick = {},
            content = {
                Column(
                    modifier = Modifier
                        .padding(32.dp)
                        .background(Color.Gray)
                        .fillMaxWidth()
                        .height(200.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Content")
                }
            }
        )
    }
}