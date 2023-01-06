package com.ada.domain.model

data class User(
    val id: UserId,
    val name: String? = null,
    val picture: String? = null,
    val emojiIcon: String? = null
)