package io.github.badgersOfKotlinForest.kotlinForest.tree

import io.github.badgersOfKotlinForest.kotlinForest.actors.Actor

interface TreeElement : Actor {
    val type: TreeElementType
}

enum class TreeElementType {
    CROWN,
    TRUNK,
    ROOT
}