package io.github.badgersOfKotlinForest.kotlinForest.tree

import io.github.badgersOfKotlinForest.kotlinForest.animals.Animal

interface Dwelling : TreeElement, Iterable<Animal> {
    fun addAnimal(animal: Animal)

    fun removeAnimal(animal: Animal)
}