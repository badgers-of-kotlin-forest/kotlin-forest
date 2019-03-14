package io.github.badgersOfKotlinForest.kotlinForest.map

import io.github.badgersOfKotlinForest.kotlinForest.actors.Actor

data class MapPosition(val x: Int, val y: Int, val z: Int)

operator fun MapPosition.plus(other: MapPosition) = MapPosition(x + other.x, y + other.y, z + other.z)

interface ActorsContainer {
    fun addActor(actor: Actor, position: MapPosition)

    fun removeActor(actor: Actor, position: MapPosition)

    fun moveActor(actor: Actor, previousPosition: MapPosition, nextPosition: MapPosition)
}

//TODO: slices for field
class ForestSight(
    private val mapHandler: ActorsContainer,
    private val mapPosition: MapPosition,
    val map: List<List<List<MapItem>>>
) {
    val length = map.size
    val width = map[0].size
    val height = map[0][0].size

    init {
        require(map.isNotEmpty() && map[0].isNotEmpty() && map[0][0].isNotEmpty()) { "map can't be empty" }
    }

    fun addActor(actor: Actor) {
        mapHandler.addActor(actor, mapPosition)
    }

    fun removeActor(actor: Actor) {
        mapHandler.removeActor(actor, mapPosition)
    }

    fun moveActor(actor: Actor, nextPosition: MapPosition) {
        mapHandler.moveActor(actor, mapPosition, mapPosition + nextPosition)
    }
}