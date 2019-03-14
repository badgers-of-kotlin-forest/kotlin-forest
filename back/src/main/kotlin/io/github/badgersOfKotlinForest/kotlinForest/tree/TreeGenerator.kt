package io.github.badgersOfKotlinForest.kotlinForest.tree

import io.github.badgersOfKotlinForest.kotlinForest.animals.Animal
import kotlin.random.Random
import kotlin.random.nextInt

const val TREE_MAX_HEIGHT = 5

object TreeGenerator {
    /**
     * Tree could be size of 1 to 5.
     * Only one root. Highest(last) element always CROWN if last isn't already ROOT.
     * Tree looks like: 1 root, k trunks, m crowns: k, m >= 0, 1 + k + m = tree height
     */
    fun genTree(): Tree {
        val height = Random.nextInt(1..TREE_MAX_HEIGHT)

        val elements = mutableListOf<TreeElement>()

        for (i in 1..height) {
            val last = elements.lastOrNull()

            elements += genTreeElement(i, height, last)
        }

        return object : Tree {
            override val elements: List<TreeElement> = elements
        }
    }

    private fun genTreeElement(elementId: Int, treeHeight: Int, prevElement: TreeElement?): TreeElement =
        when {
            prevElement == null || elementId == 1 -> genTreeElementOfType(TreeElementType.ROOT)
            elementId == treeHeight -> genTreeElementOfType(TreeElementType.CROWN)
            else -> {
                val prevType = prevElement.type
                val newType = randomTypeFromRange(
                    prevType.getNextNotGreaterByOrdinal() until TreeElementType.values().size
                )

                genTreeElementOfType(newType)
            }
        }

    private fun TreeElementType.getNextNotGreaterByOrdinal() =
        if (this == TreeElementType.ROOT) ordinal + 1 else ordinal

    private fun randomTypeFromRange(range: IntRange) = TreeElementType.values()[range.random()]

    private fun genTreeElementOfType(type: TreeElementType): TreeElement {
        val isDwelling = Random.nextInt(2) != 0

        return if (isDwelling)
            AbstractDwelling(type)
        else object : TreeElement {
            override val type: TreeElementType = type
        }
    }

    private class AbstractDwelling(override val type: TreeElementType) : Dwelling {
        private val animalsInside = mutableSetOf<Animal>()

        override fun addAnimal(animal: Animal) {
            animalsInside.add(animal)
        }

        override fun removeAnimal(animal: Animal) {
            animalsInside.remove(animal)
        }

        override fun iterator() = animalsInside.iterator()
    }

}

