package com.ada.model

data class UserModel(val id: String, val name: String? = null, val picture: String? = null) {
    companion object {
        fun mockUserId() = "SleepyKoala_1234"
        fun mock() = UserModel(id = mockUserId(), name = "Sleepy Koala", picture = null)
    }
}