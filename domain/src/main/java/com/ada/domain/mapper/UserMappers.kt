package com.ada.domain.mapper

import com.ada.data.model.UserModel
import com.ada.domain.model.User

fun UserModel.toUserOrNull(): User? {
    return User(
        id = id ?: return null,
        name = name,
        picture = picture,
        accountType = accountType ?: return null
    )
}