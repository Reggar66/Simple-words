package com.ada.simplewords.domain.models

data class User(val name: String? = null, val picture: String? = null) {
    companion object {
        fun mockUserId() = "SleepyKoala_1234"
        fun mock() = User(name = "Sleepy Koala", picture = null)
    }
}