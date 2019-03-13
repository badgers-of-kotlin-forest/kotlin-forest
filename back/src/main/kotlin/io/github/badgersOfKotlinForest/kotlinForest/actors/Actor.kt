package io.github.badgersOfKotlinForest.kotlinForest.actors

interface Actor {
    // TODO to discuss
//    val forestMap: ForestMap

    fun interactWith(other: Actor)
}