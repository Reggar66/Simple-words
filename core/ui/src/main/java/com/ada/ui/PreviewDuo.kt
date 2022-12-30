package com.ada.ui

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

/**
 * Applies [Preview] annotation for both light and dark mode.
 */
@Preview(name = "Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Light")
annotation class PreviewDuo