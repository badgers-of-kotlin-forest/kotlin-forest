package io.github.badgersOfKotlinForest.kotlinForest.animals

import io.github.badgersOfKotlinForest.kotlinForest.actors.MoveableActor

interface Animal : MoveableActor {

    val isAlive: Boolean

    /**
     * E.g. animal in dwelling or in shelter
     */
    val isHidden: Boolean
}