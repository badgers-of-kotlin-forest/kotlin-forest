package io.github.badgersOfKotlinForest.kotlinForest.animals

import io.github.badgersOfKotlinForest.kotlinForest.actors.MoveableActor
import io.github.badgersOfKotlinForest.kotlinForest.map.ForestSight
import io.github.badgersOfKotlinForest.kotlinForest.tree.Dwelling

// val damage: Int


abstract class Animal(val maxHealth: Int, val maxFullness: Int, val regenerationRate: Int) : MoveableActor {
    var health = maxHealth
        private set

    var fullness = maxFullness
        private set

    val isAlive: Boolean
        get() = health > 0

    // TODO: protected var busy = false

    protected fun getIntoDwelling(dwelling: Dwelling) {
        dwelling.addAnimal(this)
    }

    protected fun getOutDwelling(dwelling: Dwelling) {
        dwelling.removeAnimal(this)
    }

    abstract fun canEat(foodType: EatableType): Boolean

    protected fun eat(map: ForestSight, food: Eatable) {
        require(canEat(food.type)) { "Can't eat ${food.type}, check it first." }
        fullness = minOf(fullness + food.size, maxFullness)
        map.removeActor(food)
    }

//    protected fun breed(map: ForestSight, otherAnimal: Animal) {
//        // TODO add delay
//        map.addActor(otherAnimal::class.java.newInstance()!!)
//    }

    protected abstract fun breed(map: ForestSight, otherAnimal: Animal)

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