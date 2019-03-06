package io.github.badgersOfKotlinForest.kotlinForest

/*
Сначала актор двигается, потом совершает действие (взаимодействует с другими акторами)
Делается это через два цикла внутри системы, чтобы события происходили одновременно
*/
class LifeCycle(val length: Int, val width: Int) {
    val height: Int
    val map: Array<Array<Array<MapItem>>>

    init {
        height = 5
        map = Array(length) { Array(width) { Array(height) { MapItem() } } }
    }

    interface Actor {
        // Приоритет действий акторов в одной клетке
        val priority: Int
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

    class MapItem(val actors: List<Actor>) {
        constructor() : this(listOf())
    }
}

fun main() {
    println("The forest works")
}
