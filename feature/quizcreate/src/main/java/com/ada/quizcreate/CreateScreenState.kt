package com.ada.quizcreate

data class CreateScreenState(val mode: CreateScreenMode) {
    companion object {
        fun empty() = CreateScreenState(mode = CreateScreenMode.Create)
    }
}

enum class CreateScreenMode {
    Create,
    Import
}
