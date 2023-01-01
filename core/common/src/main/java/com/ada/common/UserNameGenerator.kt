package com.ada.common

object UserNameGenerator {

    private val adjectives = listOf(
        "motionless",
        "nifty",
        "zealous",
        "decisive",
        "unique",
        "weary",
        "round",
        "public",
        "gratis",
        "global",
        "abortive",
        "juicy",
        "burly",
        "famous",
        "impolite",
        "alcoholic",
        "ruthless",
        "opposite",
        "labored",
        "uppity"
    )

    private val animals = listOf(
        "mustang",
        "gnu",
        "bunny",
        "bull",
        "chipmunk",
        "walrus",
        "sheep",
        "lynx",
        "ox",
        "opossum",
        "tiger",
        "dingo",
        "warthog",
        "canary",
        "lion",
        "fox",
        "cougar",
        "llama",
        "koala",
        "bat"
    )

    fun randomName() = adjectives.random() + " " + animals.random()
}