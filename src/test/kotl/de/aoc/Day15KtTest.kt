package de.aoc

import org.junit.Test
import java.awt.Point
import java.io.File
import kotlin.test.assertEquals

/**
 * @author  Julia Burgard - burgard@synyx.de
 */
class Day15KtTest {

    @Test
    fun day15aTest() {
        val input = File("inputday15").inputStream().bufferedReader().use { it.readText() }
        val toMutableList = input.split(",").map(String::toLong).toMutableList()
        toMutableList.addAll((0..10000.toLong()).map { 0L })
        val codeList = toMutableList.toLongArray()

        val numberOfMovements = day15a(codeList)
        assertEquals(0, numberOfMovements)
    }
    @Test
    fun day15bTest() {
        val input = File("inputday15").inputStream().bufferedReader().use { it.readText() }
        val toMutableList = input.split(",").map(String::toLong).toMutableList()
        toMutableList.addAll((0..10000.toLong()).map { 0L })
        val codeList = toMutableList.toLongArray()

        val numberOfMovements = day15b(codeList)
        assertEquals(0, numberOfMovements)
    }

    @Test
    fun getNextDirectionTest() {
        var nextDirection = getNextDirection(CardinalDirection.NORTH, Point(0, 0), arrayListOf(), arrayListOf(),arrayListOf() )
        assertEquals(CardinalDirection.EAST, nextDirection)

        nextDirection = getNextDirection(CardinalDirection.EAST, Point(0, 0), arrayListOf(), arrayListOf(),arrayListOf() )
        assertEquals(CardinalDirection.SOUTH, nextDirection)

        nextDirection = getNextDirection(CardinalDirection.SOUTH, Point(0, 0), arrayListOf(), arrayListOf(),arrayListOf() )
        assertEquals(CardinalDirection.WEST, nextDirection)

        nextDirection = getNextDirection(CardinalDirection.WEST, Point(0, 0), arrayListOf(), arrayListOf(),arrayListOf() )
        assertEquals(CardinalDirection.NORTH, nextDirection)
    }


}


