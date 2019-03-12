package io.github.badgersOfKotlinForest.kotlinForest.actors

import io.github.badgersOfKotlinForest.kotlinForest.map.ForestMap
import io.github.badgersOfKotlinForest.kotlinForest.map.MapItemPosition

interface MoveableActor : Actor {

    val movementPeriod: Int

    fun move(map: ForestMap, position: MapItemPosition): MapItemPosition
}