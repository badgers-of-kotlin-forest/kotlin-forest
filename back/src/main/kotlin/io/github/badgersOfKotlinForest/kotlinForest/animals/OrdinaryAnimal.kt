package io.github.badgersOfKotlinForest.kotlinForest.animals

import io.github.badgersOfKotlinForest.kotlinForest.map.ForestMap
import io.github.badgersOfKotlinForest.kotlinForest.map.MapItemPosition
import io.github.badgersOfKotlinForest.kotlinForest.tree.Dwelling

abstract class OrdinaryAnimal(val maxHealf: Int, val maxFullness: Int, val regenRate: Int, val damage: Int) : Animal {
    var healf = maxHealf
        private set

    var fullness = maxFullness
        private set

    override val isAlive = healf > 0

    final override var isHidden = false
        private set

    // TODO: private var busy = false

    fun getIntoDwelling(dwelling: Dwelling) {
        // TODO it's not correct to get into the dwelling by yourself
        dwelling.animalsInside.add(this)
        // TODO in my opinion it’s right to have a separate world in a tree
        isHidden = true
    }

    fun getOutDwelling(dwelling: Dwelling) {
        // TODO it's not correct to get into the dwelling by yourself
        dwelling.animalsInside.remove(this)
        // TODO in my opinion it’s right to have a separate world in a tree
        isHidden = false
    }

    abstract fun canEat(foodType: EatableType): Boolean

    fun eat(map: ForestMap, position: MapItemPosition, food: Eatable) {
        if (canEat(food.type)) {
            fullness = (fullness + food.size) % maxFullness
            map.removeActor(food, position)
        }
    }

    fun breed(map: ForestMap, position: MapItemPosition, otherAnimal: Animal) {
        // TODO add delay
        map.addActor(otherAnimal::class.java.newInstance()!!, position)
    }

    fun wellbeing() {
        if (fullness == maxFullness) {
            healf += regenRate
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