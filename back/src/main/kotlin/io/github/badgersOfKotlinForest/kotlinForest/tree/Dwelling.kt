package io.github.badgersOfKotlinForest.kotlinForest.tree

import io.github.badgersOfKotlinForest.kotlinForest.animals.Animal

interface Dwelling : TreeElement {
    val animalsInside: MutableList<Animal>
}