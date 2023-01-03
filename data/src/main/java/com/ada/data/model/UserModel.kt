package com.ada.data.model

data class UserModel(
    val id: String? = null,
    val name: String? = null,
    val picture: String? = null,
    val accountType: UserAccountType? = null,
    val emojiIcon: String? = null,
) {
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

enum class UserAccountType {
    Anonymous,
    Permanent
}