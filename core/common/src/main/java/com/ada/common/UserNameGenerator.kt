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
        "sleepy",
        "lazy",
        "hairy",
        "abortive",
        "juicy",
        "burly",
        "famous",
        "impolite",
        "hypnotic",
        "ruthless",
        "opposite",
        "labored",
        "uppity",
        "annoying",
        "critical",
        "energetic"
    )

    val animals = listOf(
        "mustang" to "\uD83D\uDC34",
        "owl" to "\uD83E\uDD89",
        "bunny" to "\uD83D\uDC07",
        "badger" to "\uD83E\uDDA1",
        "chipmunk" to "\uD83D\uDC3F️",
        "duck" to "\uD83E\uDD86",
        "sheep" to "\uD83D\uDC11",
        "skunk" to "\uD83E\uDDA8",
        "ox" to "\uD83D\uDC02",
        "elephant" to "\uD83D\uDC18",
        "chick" to "\uD83D\uDC24",
        "mouse" to "\uD83D\uDC01",
        "beaver" to "\uD83E\uDDAB",
        "sloth" to "\uD83E\uDDA5",
        "lion" to "\uD83E\uDD81",
        "fox" to "\uD83E\uDD8A",
        "otter" to "\uD83E\uDDA6",
        "llama" to "\uD83E\uDD99",
        "koala" to "\uD83D\uDC28",
        "bat" to "\uD83E\uDD87"
    )

    fun randomName(): String {
        return (adjectives.shuffled().random()
            .replaceFirstChar { it.uppercase() } + " "
                + animals.shuffled().random().first
            .replaceFirstChar { it.uppercase() })
            .also { debugLog { "generated name: $it" } }
    }

    fun randomNameWithIcon(): NameWithEmoji {
        val animalRandom = animals.shuffled().random()
        val adjectiveRandom = adjectives.shuffled().random()
        return NameWithEmoji(
            adjective = adjectiveRandom,
            animal = animalRandom.first,
            emoji = animalRandom.second
        )
    }

    data class NameWithEmoji(val adjective: String, val animal: String, val emoji: String) {
        fun name() =
            adjective.replaceFirstChar { it.uppercase() } + " " + animal.replaceFirstChar { it.uppercase() }
    }
}