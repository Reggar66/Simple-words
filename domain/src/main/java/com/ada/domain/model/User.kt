package com.ada.domain.model

data class User(
    val id: String,
    val name: String? = null,
    val picture: String? = null
)