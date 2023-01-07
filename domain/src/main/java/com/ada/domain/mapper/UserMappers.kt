package com.ada.domain.mapper

import com.ada.data.model.UserIdModel
import com.ada.data.model.UserModel
import com.ada.domain.model.User
import com.ada.domain.model.UserId

fun UserModel.toUserOrNull(): User? {
    return User(
        id = UserId(uid = id ?: return null, userAccountType = accountType ?: return null),
        name = name,
        picture = picture,
        emojiIcon = emojiIcon
    )
}

fun UserIdModel.toUserIdOrNull(): UserId? {
    return UserId(
        uid = uid ?: return null,
        userAccountType = userAccountType ?: return null
    )
}

fun User.toUserModel(): UserModel {
    return UserModel(
        id = id.uid,
        name = name,
        picture = picture,
        accountType = id.userAccountType,
        emojiIcon = emojiIcon
    )
}