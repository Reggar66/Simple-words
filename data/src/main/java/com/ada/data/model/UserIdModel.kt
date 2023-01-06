package com.ada.data.model

data class UserIdModel(
    val uid: String? = null,
    val userAccountType: UserAccountType? = null
) {
    companion object {
        fun mock() = UserIdModel(
            uid = "SleepyKoala_1234",
            userAccountType = UserAccountType.Anonymous
        )
    }
}

enum class UserAccountType {
    Anonymous,
    Permanent
}