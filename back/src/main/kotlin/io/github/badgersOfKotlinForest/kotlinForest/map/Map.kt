package io.github.badgersOfKotlinForest.kotlinForest.map

import io.github.badgersOfKotlinForest.kotlinForest.LifeCycle
import io.github.badgersOfKotlinForest.kotlinForest.actors.Actor

data class MapPosition(val x: Int, val y: Int, val z: Int)

operator fun MapPosition.plus(other: MapPosition) = MapPosition(x + other.x, y + other.y, z + other.z)

//TODO: slices for field
class ForestMap(
    protected val lifeCycle: LifeCycle,
    private val mapPosition: MapPosition,
    val map: Array<Array<Array<MapItem>>>
) {
    val length = map.size
    val width = map[0].size
    val height = map[0][0].size

    init {
        require(map.isNotEmpty() && map[0].isNotEmpty() && map[0][0].isNotEmpty()) { "map can't be empty" }
    }

    fun addActor(actor: Actor, position: MapPosition) {
        lifeCycle.addActor(actor, mapPosition + position)
    }

    fun removeActor(actor: Actor, position: MapPosition) {
        lifeCycle.removeActor(actor, mapPosition + position)
    }

    fun moveActor(actor: Actor, previousPosition: MapPosition, nextPosition: MapPosition) {
        lifeCycle.moveActor(actor, mapPosition + previousPosition, mapPosition + nextPosition)
    }
}