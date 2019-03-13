package io.github.badgersOfKotlinForest.kotlinForest.actors

import io.github.badgersOfKotlinForest.kotlinForest.map.ForestMap

interface MoveableActor : Actor {

    val sight: Int

    fun move(forestMap: ForestMap)

    fun interact(forestMap: ForestMap)
}