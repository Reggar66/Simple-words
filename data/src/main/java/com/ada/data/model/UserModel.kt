package com.ada.data.model

import com.ada.common.Key

data class UserModel(
    val id: String? = null,
    val name: String? = null,
    val picture: String? = null,
    val accountType: UserAccountType? = null,
    val emojiIcon: String? = null,
) {

    fun toMap() = mapOf<Key, Any?>(
        "id" to id,
        "name" to name,
        "picture" to picture,
        "accountType" to accountType,
        "emojiIcon" to emojiIcon
    )

    companion object {
        fun mock() = UserModel(
            id = "SleepyKoala_1234",
            name = "Sleepy Koala",
            picture = null,
            accountType = UserAccountType.Anonymous,
            emojiIcon = "\uD83D\uDC28"
        )
    }
}