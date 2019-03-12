package io.github.badgersOfKotlinForest.kotlinForest.map

//TODO: slices for field
class ForestMap(private val map: Array<Array<Array<MapItem>>>)

data class MapItemPosition(val x: Int, val y: Int, val z: Int)