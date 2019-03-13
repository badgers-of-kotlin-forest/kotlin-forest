package io.github.badgersOfKotlinForest.kotlinForest.animals

import io.github.badgersOfKotlinForest.kotlinForest.actors.MoveableActor
import io.github.badgersOfKotlinForest.kotlinForest.map.ForestMap
import io.github.badgersOfKotlinForest.kotlinForest.map.MapPosition
import io.github.badgersOfKotlinForest.kotlinForest.tree.Dwelling

// val damage: Int


abstract class Animal(val maxHealth: Int, val maxFullness: Int, val regenerationRate: Int) : MoveableActor {
    var health = maxHealth
        private set

    var fullness = maxFullness
        private set

    val isAlive: Boolean
        get() = health > 0

    var isHidden = false
        private set

    // TODO: protected var busy = false

    protected fun getIntoDwelling(dwelling: Dwelling) {
        // TODO it's not correct to get into the dwelling by yourself
        dwelling.animalsInside.add(this)
        // TODO in my opinion it’s right to have a separate world in a tree
        isHidden = true
    }

    protected fun getOutDwelling(dwelling: Dwelling) {
        // TODO it's not correct to get into the dwelling by yourself
        dwelling.animalsInside.remove(this)
        // TODO in my opinion it’s right to have a separate world in a tree
        isHidden = false
    }

    abstract fun canEat(foodType: EatableType): Boolean

    protected fun eat(map: ForestMap, position: MapPosition, food: Eatable) {
        require(canEat(food.type)) { "Can't eat ${food.type}, check it first." }
        fullness = minOf(fullness + food.size, maxFullness)
        map.removeActor(food, position)
    }

    protected fun breed(map: ForestMap, position: MapPosition, otherAnimal: Animal) {
        // TODO add delay
        map.addActor(otherAnimal::class.java.newInstance()!!, position)
    }

    protected fun wellbeing() {
        if (fullness == maxFullness) {
            health = (health + regenerationRate) % maxFullness
        }
    }

    //    fun getDamage(damage: Int) {
//        healf = Integer.max(healf - damage, 0)
//    }
//
//    fun beat(otherAnimal: OrdinaryAnimal) {
//        otherAnimal.getDamage(damage)
//    }
}