package de.aoc

import org.junit.Test
import java.awt.Point
import kotlin.test.assertEquals

/**
 * @author  Julia Burgard - burgard@synyx.de
 */
class Day10KtTest {

    @Test
    fun parseMap() {
        val map = ".#.\n" +
                "..#\n" +
                "##."
        val asteroidMap = parseMap(map);

        assertEquals(listOf(Point(1, 0), Point(2, 1), Point(0, 2), Point(1, 2)), asteroidMap.positions)
    }

    @Test
    fun isOnSameVerticalOrHorizontal() {
        val onSameVertical = isOnSameVerticalHorizontal(Point(0, 0), Point(0, 1))
        assertEquals(true, onSameVertical)

        val onSameHorizontal = isOnSameVerticalHorizontal(Point(0, 0), Point(1, 0))
        assertEquals(true, onSameHorizontal)

        val notOnSameVertialOrHorizontal = isOnSameVerticalHorizontal(Point(0, 0), Point(1, 1))
        assertEquals(false, notOnSameVertialOrHorizontal)
    }

    @Test
    fun isOnSameDiagonal() {
        val onSameDiagonal = isOnSameDiagonal(Point(0, 0), Point(1, 1))
        assertEquals(true, onSameDiagonal)

        val notOnSameDiagonal = isOnSameDiagonal(Point(0, 0), Point(0, 1))
        assertEquals(false, notOnSameDiagonal)
    }

    @Test
    fun getAllPointsHorizontalOrVerticalTest() {
        val map = "###\n" +
                "###\n" +
                "###"
        val mapPoints = parseMap(map)

        var horizontalPoints = getAllPointsHorizontalOrVertical(Point(0, 0), Point(2, 0), mapPoints.positions)
        assertEquals(listOf(Point(0, 0), Point(1, 0), Point(2, 0)), horizontalPoints)

        horizontalPoints = getAllPointsHorizontalOrVertical(Point(3, 0), Point(1, 0), mapPoints.positions)
        assertEquals(listOf(Point(1, 0), Point(2, 0), Point(3, 0)), horizontalPoints)

        val verticalPoints = getAllPointsHorizontalOrVertical(Point(0, 0), Point(0, 2), mapPoints.positions)
        assertEquals(listOf(Point(0, 0), Point(0, 1), Point(0, 2)), verticalPoints)
    }

    @Test
    fun getAllPointsDiagonalTest() {
        val map = "###\n" +
                "###\n" +
                "###"
        val mapPoints = parseMap(map)

        var diagonalPoints = getAllPointsDiagonal(Point(0, 0), Point(2, 2), mapPoints)
        assertEquals(listOf(Point(0, 0), Point(1, 1), Point(2, 2)), diagonalPoints)

        diagonalPoints = getAllPointsDiagonal(Point(2, 2), Point(0, 0), mapPoints)
        assertEquals(listOf(Point(0, 0), Point(1, 1), Point(2, 2)), diagonalPoints)

    }

    @Test
    fun getLowestCommonDivisorTest() {
        var lowestCommonDivisor = getLowestCommonDivisor(Pair(4, 12));
        assertEquals(2, lowestCommonDivisor)

        lowestCommonDivisor = getLowestCommonDivisor(Pair(3, 18));
        assertEquals(3, lowestCommonDivisor)
    }

    @Test
    fun getAllPointInBetween() {
        val map = ".....\n" +
                ".....\n" +
                "....."
        val mapPoints = parseMap(map)

        var diagonalPoints = getAllPointsInBetween(Point(0, 0), Point(2, 1), AsteroidMap(emptyList()))
        assertEquals(listOf(Point(2, 1), Point(4, 2)), diagonalPoints)

        diagonalPoints = getAllPointsInBetween(Point(4, 4), Point(2, 1), AsteroidMap(emptyList()))
        assertEquals(listOf(Point(2, 1), Point(0, 0)), diagonalPoints)

    }

}
