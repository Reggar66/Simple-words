package com.ada.domain.model

import com.ada.data.model.UserAccountType

data class User(
    val id: String,
    val name: String? = null,
    val picture: String? = null,
    val accountType: UserAccountType,
    val emojiIcon: String? = null
)