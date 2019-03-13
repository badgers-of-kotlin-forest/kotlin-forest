package io.github.badgersOfKotlinForest.kotlinForest

import io.github.badgersOfKotlinForest.kotlinForest.actors.Actor
import io.github.badgersOfKotlinForest.kotlinForest.actors.MoveableActor
import io.github.badgersOfKotlinForest.kotlinForest.animals.Animal
import io.github.badgersOfKotlinForest.kotlinForest.map.ForestMap
import io.github.badgersOfKotlinForest.kotlinForest.map.MapItem
import io.github.badgersOfKotlinForest.kotlinForest.map.MapPosition

class LifeCycle(val length: Int, val width: Int) {
    private val map = Array(length) { Array(width) { Array(height) { mutableSetOf<Actor>() } } }
    val height = 5

    constructor(map: Array<Array<Array<MutableSet<Actor>>>>) : this(map.size, map[0].size) {
        require(map.isNotEmpty() && map[0].isNotEmpty() && map[0][0].isNotEmpty()) { "map can't be empty" }
        require(height == map[0][0].size) { "height is constant and equals to {$height}" }
    }

    private var toAdd: MutableList<Pair<Actor, MapPosition>> = mutableListOf()

    private var toRemove: MutableList<Pair<Actor, MapPosition>> = mutableListOf()

    //TODO: discuss z slices
    private fun getSlice(position: MapPosition, radius: Int): ForestMap {
        val (x, y, z) = position
        val slice = map.slice(IntRange(Integer.max(0, x - radius), Integer.min(x + radius, length))).map {
            it.slice(IntRange(Integer.max(0, y - radius), Integer.min(y + radius, width))).map {
                it.slice(IntRange(0, z)).map { MapItem(it) }.toTypedArray()
            }.toTypedArray()
        }.toTypedArray()
        return ForestMap(this, position, slice)
    }

    fun addActor(actor: Actor, position: MapPosition) {
        toAdd.add(Pair(actor, position))
    }

    private fun addActors() {
        toAdd.forEach { (actor, position) -> map[position.x][position.y][position.z].add(actor) }
        toAdd = mutableListOf()
    }

    fun removeActor(actor: Actor, position: MapPosition) {
        toRemove.add(Pair(actor, position))
    }

    private fun removeActors() {
        toRemove.forEach { (actor, position) -> map[position.x][position.y][position.z].remove(actor) }
        toRemove = mutableListOf()
    }

    fun moveActor(actor: Actor, previousPosition: MapPosition, nextPosition: MapPosition) {
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
                    zs.forEach {
                        if (it is MoveableActor) {
                            it.move(getSlice(MapPosition(x, y, z), it.sight))
                        }
                    }
                }
            }
        }
        require(toAdd.size == toRemove.size) { "after the movement of the actors their number has changed" }
        updateActorsPositions()
    }

    private fun interactActors() {
        map.forEachIndexed { x, xs ->
            xs.forEachIndexed { y, ys ->
                ys.forEachIndexed { z, actors ->
                    for (actor in actors.filter { (it is Animal && it.isHidden) || it !is Animal }) {
                        for (otherActor in actors.filter { actor !== it }) {
                            if (actor is MoveableActor)
                                actor.interact(getSlice(MapPosition(x, y, z), actor.sight))
                        }
                    }
                }
            }
        }
        updateActorsPositions()
    }

    private fun actActors() {
        map.forEach { it.forEach { it.forEach { it.forEach { it.act() } } } }
    }

    fun tick() {
        moveActors()
        interactActors()
        actActors()
    }
}