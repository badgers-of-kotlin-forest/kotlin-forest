package io.github.badgersOfKotlinForest.kotlinForest.tree

import io.github.badgersOfKotlinForest.kotlinForest.animals.Animal

interface Dwelling : TreeElement {
    val animalsInside: MutableList<Animal>

    // TODO it's not correct to get into the dwelling by yourself
//    fun getInto(animal: Animal)
//
//    fun getOut(animal: Animal)
}