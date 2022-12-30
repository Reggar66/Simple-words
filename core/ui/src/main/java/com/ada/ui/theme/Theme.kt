package com.ada.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = BlueLight,
    primaryVariant = BlueDark,
    onPrimary = Color.Black,

    background = Gray3,
    onBackground = Color.White,
    surface = Gray3,
    onSurface = Color.White
)

private val LightColorPalette = lightColors(
    primary = BlueLight,
    primaryVariant = BlueDark,
    secondary = Teal200

    /* Other default colors to override
background = Color.White,
surface = Color.White,
onPrimary = Color.White,
onSecondary = Color.Black,
onBackground = Color.Black,
onSurface = Color.Black,
*/
)

val Colors.bottomSheetSurface get() = if (isLight) surface else Gray2
val Colors.itemBackground get() = if (isLight) surface else Gray1
val Colors.itemBackgroundLearned get() = if (isLight) surface else Gray3

@Composable
fun SimpleWordsTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}