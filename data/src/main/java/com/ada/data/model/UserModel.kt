package com.ada.data.model

data class UserModel(val id: String, val name: String? = null, val picture: String? = null) {
    companion object {
        fun mock() = UserModel(id = "SleepyKoala_1234", name = "Sleepy Koala", picture = null)
    }
}