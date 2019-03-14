package io.github.badgersOfKotlinForest.kotlinForest.actors

import io.github.badgersOfKotlinForest.kotlinForest.map.ForestSight

interface MoveableActor : Actor {

    val sight: Int

    fun move(forestMap: ForestSight)

    fun act(forestMap: ForestSight)
}