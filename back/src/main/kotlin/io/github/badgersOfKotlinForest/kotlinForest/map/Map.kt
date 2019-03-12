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
    private val period: Long = Long.MAX_VALUE
    val length: Int
    val width: Int
    val height: Int

    init {
        length = map.size
        width = map[0].size
        height = 5
        assert(height == map[0][0].size)
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

    private fun moveActor(actor: Actor, prevPosition: MapItemPosition, position: MapItemPosition) {
        map[prevPosition.x][prevPosition.y][prevPosition.z].actors.remove(actor)
        map[position.x][position.y][position.z].actors.remove(actor)
    }

    private fun moveActors() {
        val moves = mutableListOf<Triple<Actor, MapItemPosition, MapItemPosition>>()
        map.forEachIndexed { x, xs ->
            xs.forEachIndexed { y, ys ->
                ys.forEachIndexed { z, zs ->
                    zs.actors.forEach {
                        if (it is MoveableActor && time % it.movementPeriod == 0L) {
                            val position = MapItemPosition(x, y, z)
                            moves.add(Triple(it, position, it.move(this, position)))
                        }
                    }
                }
            }
        }
        moves.forEach { moveActor(it.first, it.second, it.third) }
    }

    private fun interactActors() {
        map.forEachIndexed { x, xs ->
            xs.forEachIndexed { y, ys ->
                ys.forEachIndexed { z, mapItem ->
                    val actors = mapItem.actors
                    val firstActor = actors.first()
                    actors.removeAt(0)
                    var curActor = firstActor
                    do {
                        actors.forEach { curActor.interactWith(it) }
                        actors.add(curActor)
                        curActor = actors.first()
                        actors.removeAt(0)
                    } while (curActor != firstActor)
                }
            }
        }
    }

    fun tick() {
        moveActors()
        interactActors()
        time = (time + 1) / period
    }
}