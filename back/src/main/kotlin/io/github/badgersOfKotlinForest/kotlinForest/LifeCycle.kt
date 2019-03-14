package io.github.badgersOfKotlinForest.kotlinForest

import io.github.badgersOfKotlinForest.kotlinForest.actors.Actor
import io.github.badgersOfKotlinForest.kotlinForest.actors.MoveableActor
import io.github.badgersOfKotlinForest.kotlinForest.map.ActorsContainer
import io.github.badgersOfKotlinForest.kotlinForest.map.ForestSight
import io.github.badgersOfKotlinForest.kotlinForest.map.MapItem
import io.github.badgersOfKotlinForest.kotlinForest.map.MapPosition

class LifeCycle(val length: Int, val width: Int) : ActorsContainer {
    private val map = List(length) { List(width) { List(height) { mutableSetOf<Actor>() } } }
    val height = 5

    constructor(map: Array<Array<Array<MutableSet<Actor>>>>) : this(map.size, map[0].size) {
        require(map.isNotEmpty() && map[0].isNotEmpty() && map[0][0].isNotEmpty()) { "map can't be empty" }
        require(height == map[0][0].size) { "height is constant and equals to {$height}" }
    }

    private val toMove: MutableList<Triple<Actor, MapPosition, MapPosition>> = mutableListOf()

    //TODO: discuss z slices
    private fun getSlice(position: MapPosition, radius: Int): ForestSight {
        val (x, y, z) = position
        val slice = map.slice(IntRange(Integer.max(0, x - radius), Integer.min(x + radius, length))).map {
            it.slice(IntRange(Integer.max(0, y - radius), Integer.min(y + radius, width))).map {
                it.slice(IntRange(0, z)).map { MapItem(it) }
            }
        }
        return ForestSight(this, position, slice)
    }

    override fun addActor(actor: Actor, position: MapPosition) {
        map[position.x][position.y][position.z].add(actor)
    }

    override fun removeActor(actor: Actor, position: MapPosition) {
        map[position.x][position.y][position.z].remove(actor)
    }

    override fun moveActor(actor: Actor, previousPosition: MapPosition, nextPosition: MapPosition) {
        toMove.add(Triple(actor, previousPosition, nextPosition))
    }

    private fun doMoveActors() {
        toMove.forEach { (actor, previosPosition, nextPosition) ->
            removeActor(actor, previosPosition)
            addActor(actor, nextPosition)
        }
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
        doMoveActors()
    }

    private fun actActors() {
        map.forEachIndexed { x, xs ->
            xs.forEachIndexed { y, ys ->
                ys.forEachIndexed { z, actors ->
                    for (actor in actors) {
                        for (otherActor in actors.filter { actor !== it && actor in map[x][y][z] }) {
                            if (actor is MoveableActor)
                                actor.act(getSlice(MapPosition(x, y, z), actor.sight))
                        }
                    }
                }
            }
        }
    }

    fun tick() {
        moveActors()
        actActors()
    }
}