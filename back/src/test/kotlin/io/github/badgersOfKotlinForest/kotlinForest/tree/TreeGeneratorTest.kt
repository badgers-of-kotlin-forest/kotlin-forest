package io.github.badgersOfKotlinForest.kotlinForest.tree

import org.junit.Test
import kotlin.test.*

class TreeGeneratorTest {
    @Test fun `stress check tree generator`() {
        repeat(1000) {
            checkTree(TreeGenerator.genTree())
        }
    }

    private fun checkTree(tree: Tree) {
        assert(tree.elements.size <= TREE_MAX_HEIGHT)
        assert(tree.elements.isNotEmpty())

        assertEquals(TreeElementType.ROOT, tree.elements.first().type)

        if (tree.elements.size > 1)
            assertEquals(TreeElementType.CROWN, tree.elements.last().type)

        tree.elements.subList(1, tree.elements.size).forEach {
            assertNotEquals(TreeElementType.ROOT, it.type)
        }

        for (i in 1 until tree.elements.size) {
            val prev = tree.elements[i - 1]
            val current = tree.elements[i]

            assert(prev.type.ordinal <= current.type.ordinal)
        }
    }
}