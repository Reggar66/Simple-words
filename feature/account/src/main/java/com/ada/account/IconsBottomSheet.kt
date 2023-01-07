package com.ada.account

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ada.common.OnClick
import com.ada.common.OnClickTakes
import com.ada.common.UserNameGenerator
import com.ada.ui.PreviewContainer
import com.ada.ui.PreviewDuo
import com.ada.ui.components.BottomSheetContainer
import com.ada.ui.theme.itemBackground

@Composable
fun IconsBottomSheet(onIconClick: OnClickTakes<String>, onCloseClick: OnClick) {
    BottomSheetContainer(title = "Icons", onCloseClick = onCloseClick) {
        Column {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 80.dp),
                verticalArrangement = Arrangement.spacedBy(
                    8.dp,
                    alignment = Alignment.CenterVertically
                ),
                horizontalArrangement = Arrangement.spacedBy(
                    8.dp,
                    alignment = Alignment.CenterHorizontally
                ),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(items = UserNameGenerator.animals) { item ->
                    IconItem(emoji = item.second, onIconClick = onIconClick)
                }
            }
        }
    }
}

@Composable
private fun IconItem(emoji: String, onIconClick: OnClickTakes<String>) {
    Box(contentAlignment = Alignment.Center) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .background(
                    color = MaterialTheme.colors.itemBackground,
                    shape = CircleShape
                )
                .clip(CircleShape)
                .clickable { onIconClick(emoji) },
            contentAlignment = Alignment.Center
        ) {
            Text(modifier = Modifier.padding(4.dp), text = emoji, fontSize = 50.sp)
        }
    }
}

@PreviewDuo
@Composable
private fun IconsBottomSheetPreview() {
    PreviewContainer {
        IconsBottomSheet(onIconClick = {}, onCloseClick = {})
    }
}