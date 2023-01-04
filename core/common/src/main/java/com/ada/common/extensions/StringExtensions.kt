package com.ada.common.extensions

import android.util.Patterns

fun String.isValidEmail() = Patterns.EMAIL_ADDRESS.matcher(this).matches()