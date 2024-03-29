@file:OptIn(ExperimentalMaterialApi::class)

package com.ada.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.ada.common.OnClick
import com.ada.common.extensions.toPx
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun SwipeMenu(
    state: SwipeableState<SwipeValue> = rememberSwipeableState(initialValue = SwipeValue.Default),
    buttonSize: Dp = 50.dp,
    onRemoveClick: OnClick,
    onEditClick: OnClick,
    content: @Composable RowScope.() -> Unit
) {
    val scope = rememberCoroutineScope()

    val isRtl = LocalLayoutDirection.current == LayoutDirection.Rtl

    val buttonSizePx = buttonSize.toPx()

    val anchors = mapOf(
        0f to SwipeValue.Default,
        buttonSizePx to SwipeValue.ToRight,
        -buttonSizePx to SwipeValue.ToLeft
    )
    Box(
        modifier = Modifier.swipeable(
            state = state,
            anchors = anchors,
            orientation = Orientation.Horizontal,
            enabled = true,
            reverseDirection = isRtl,
            thresholds = { _, _ -> FractionalThreshold(0.8f) },
        )
    ) {
        /* Background */
        Row(
            modifier = Modifier.matchParentSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            content = {
                Box(
                    modifier = Modifier
                        //.size(buttonSize)
                        .fillMaxHeight()
                        .width(buttonSize)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(bounded = false, radius = buttonSize / 2),
                            onClick = {
                                onRemoveClick()
                                scope.launch {
                                    state.snapTo(SwipeValue.Default)
                                }
                            })
                        .padding(4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(imageVector = Icons.Rounded.Delete, contentDescription = null)
                }
                Spacer(modifier = Modifier.weight(1f))
                Box(
                    modifier = Modifier
                        //.size(buttonSize)
                        .fillMaxHeight()
                        .width(buttonSize)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(bounded = false, radius = buttonSize / 2),
                            onClick = { onEditClick() })
                        .padding(4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(imageVector = Icons.Rounded.Edit, contentDescription = null)
                }
            }
        )
        /* Content */
        Row(
            modifier = Modifier.offset { IntOffset(state.offset.value.roundToInt(), 0) },
            content = content,
        )
    }
}


enum class SwipeValue {
    Default,
    ToRight,
    ToLeft
}