package com.ada.data.model

data class UserModel(
    val id: String? = null,
    val name: String? = null,
    val picture: String? = null
) {
    companion object {
        fun mock() = UserModel(id = "SleepyKoala_1234", name = "Sleepy Koala", picture = null)
    }
}