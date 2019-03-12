package io.github.badgersOfKotlinForest.kotlinForest.animals

import io.github.badgersOfKotlinForest.kotlinForest.actors.Actor

interface Eatable : Actor {
    val type: EatableType

    val size: Int
}

enum class EatableType {
    PLANT,
    ANIMAL
}