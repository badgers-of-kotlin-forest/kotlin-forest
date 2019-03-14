package io.github.badgersOfKotlinForest.kotlinForest.map

import io.github.badgersOfKotlinForest.kotlinForest.actors.Actor

class MapItem(private val actors: MutableSet<Actor> = mutableSetOf()) : Iterable<Actor> {
    override fun iterator(): Iterator<Actor> = actors.iterator()

    fun addActor(actor: Actor) {
        actors.add(actor)
    }

    fun removeActor(actor: Actor) {
        actors.remove(actor)
    }
}