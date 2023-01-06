package com.ada.domain.model

import com.ada.data.model.UserAccountType

data class UserId(
    val uid: String,
    val userAccountType: UserAccountType
)