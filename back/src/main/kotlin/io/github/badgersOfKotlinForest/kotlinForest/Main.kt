package io.github.badgersOfKotlinForest.kotlinForest

/*
Сначала актор двигается, потом совершает действие (взаимодействует с другими акторами)
Делается это через два цикла внутри системы, чтобы события происходили одновременно
*/
class LifeCycle(val length: Int, val width: Int) {
    val time: Long
    val period: Long
    val height: Int
    val map: Array<Array<Array<MapItem>>>

    init {
        time = 0
        period = Long.MAX_VALUE
        height = 5
        map = Array(length) { Array(width) { Array(height) { MapItem() } } }
    }

    interface Actor {
        //        // Приоритет действий акторов в одной клетке
//        val priority: Int
        // Периуд передвижения актора (обратно пропорционален его скорости)
        val movementPeriod: Int

        /**
         * Перемещение актора по карте
         * @param map карта леса
         * @param pos позиция актора
         * @return новазя позиция актора
         */
        fun move(map: Array<Array<Array<MapItem>>>, pos: Triple<Int, Int, Int>): Triple<Int, Int, Int>

        /**
         * Взаимодействие актора с другими акторами
         * @param otherActor актор, с которым будет происходить взаимодействие
         * @return otherActor после взаимодействия с нашшим актором
         */
        fun interact(otherActor: Actor): Actor?
    }

    class MapItem(val actors: MutableList<Actor>) {
        constructor() : this(mutableListOf())
    }

    fun tick() {
        val newMap = Array(length) { Array(width) { Array(height) { MapItem() } } }
        for (x in 0..map.size) {
            for (y in 0..map[x].size) {
                for (z in 0..map[x][y].size) {
                    for (actor in map[x][y][z].actors) {
                        if (time % actor.movementPeriod == 0L) {
                            val (x1, y1, z1) = actor.move(map, Triple(x, y, z))
                            newMap[x1][y1][z1].actors.add(actor)
                        }
                    }
                }
            }
        }

        for (x in 0..newMap.size) {
            for (y in 0..newMap[x].size) {
                for (z in 0..newMap[x][y].size) {
                    val actors = newMap[x][y][z].actors
                    if (actors.size > 1) {
                        val firstActor = actors.first()
                        actors.removeAt(0)
                        var curActor = firstActor
                        do {
                            for (actor in actors) {
                                curActor.interact(actor)
                            }
                            actors.add(curActor)
                            curActor = actors.first()
                            actors.removeAt(0)
                        } while (curActor != firstActor)
                    }
                    newMap[x][y][z] = MapItem(actors)
                }
            }
        }
    }
}

fun main() {
    println("The forest works")
}
