package io.github.badgersOfKotlinForest.kotlinForest.map

import io.github.badgersOfKotlinForest.kotlinForest.actors.Actor
import io.github.badgersOfKotlinForest.kotlinForest.actors.MoveableActor

data class MapItemPosition(val x: Int, val y: Int, val z: Int)

//TODO: slices for field
/*
Сначала актор двигается, потом совершает действие (взаимодействует с другими акторами)
Делается это через два цикла внутри системы, чтобы события происходили одновременно
*/
class ForestMap(private val map: Array<Array<Array<MapItem>>>) {
    private var time: Long = 0
    val length = map.size
    val width = map[0].size
    val height = 5

    private var toAdd: MutableList<Pair<Actor, MapItemPosition>> = mutableListOf()

    private var toRemove: MutableList<Pair<Actor, MapItemPosition>> = mutableListOf()

    init {
        require(map.isNotEmpty() && map[0].isNotEmpty() && map[0][0].isNotEmpty()) { "map can't be empty" }
        require(height == map[0][0].size) { "height is constant and equals to 5" }
    }

//    //TODO: discuss z slices
//    private fun getSlice(position: MapItemPosition, radius: Int): Array<Array<Array<MapItem>>> {
//        val (x, y, z) = position
//        return map.slice(IntRange(Integer.max(0, x-radius), Integer.min(x+radius, length))).map {
//            it.slice(IntRange(Integer.max(0, y-radius), Integer.min(y+radius, height))).map {
//                it.sliceArray(IntRange(0, z))
//            }.toTypedArray()
//        }.toTypedArray()
//    }

    fun addActor(actor: Actor, position: MapItemPosition) = toAdd.add(Pair(actor, position))

    private fun addActors() {
        toAdd.forEach { (actor, position) -> map[position.x][position.y][position.z].actors.add(actor) }
        toAdd = mutableListOf()
    }

    fun removeActor(actor: Actor, position: MapItemPosition) = toRemove.add(Pair(actor, position))

    private fun removeActors() {
        toRemove.forEach { (actor, position) -> map[position.x][position.y][position.z].actors.remove(actor) }
        toRemove = mutableListOf()
    }

    fun moveActor(actor: Actor, previousPosition: MapItemPosition, nextPosition: MapItemPosition) {
        removeActor(actor, previousPosition)
        addActor(actor, nextPosition)
    }

    private fun updateActorsPositions() {
        removeActors()
        addActors()
    }

    private fun moveActors() {
        map.forEachIndexed { x, xs ->
            xs.forEachIndexed { y, ys ->
                ys.forEachIndexed { z, zs ->
                    zs.actors.forEach {
                        if (it is MoveableActor && time % it.movementPeriod == 0L) {
                            val position = MapItemPosition(x, y, z)
                            it.move(this, position)
                        }
                    }
                }
            }
        }
        require(toAdd.size == toRemove.size) { "It is understood that the actors all walk first, then all interact" }
        updateActorsPositions()
    }

    private fun interactActors() {
        map.forEachIndexed { x, xs ->
            xs.forEachIndexed { y, ys ->
                ys.forEachIndexed { z, mapItem ->
                    val actors = mapItem.actors
                    for (actor in actors) {
                        for (otherActor in actors.filter { actor !== it }) {
                            actor.interactWith(otherActor)
                        }
                    }
                }
            }
        }
        updateActorsPositions()
    }

    fun tick() {
        moveActors()
        interactActors()
        time = maxOf(0, time + 1)
    }
}