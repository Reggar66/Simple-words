package com.ada.simplewords.domain.models

data class User(val id: String, val name: String? = null, val picture: String? = null) {
    companion object {
        fun mockUserId() = "SleepyKoala_1234"
        fun mock() = User(id = mockUserId(), name = "Sleepy Koala", picture = null)
    }
}