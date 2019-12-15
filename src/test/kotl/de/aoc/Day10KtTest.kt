package de.aoc

import org.junit.Test
import java.awt.Point
import java.io.File
import kotlin.test.assertEquals

/**
 * @author  Julia Burgard - burgard@synyx.de
 */
class Day10KtTest {



    @Test
    fun day10bExampleOne() {
        val map = ".#..##.###...#######\n" +
                "##.############..##.\n" +
                ".#.######.########.#\n" +
                ".###.#######.####.#.\n" +
                "#####.##.#.##.###.##\n" +
                "..#####..#.#########\n" +
                "####################\n" +
                "#.####....###.#.#.##\n" +
                "##.#################\n" +
                "#####.##.###..####..\n" +
                "..######..##.#######\n" +
                "####.##.####...##..#\n" +
                ".#####..#.######.###\n" +
                "##...#.##########...\n" +
                "#.##########.#######\n" +
                ".####.#.###.###.#.##\n" +
                "....##.##.###..#####\n" +
                ".#.#.###########.###\n" +
                "#.#.#.#####.####.###\n" +
                "###.##.####.##.#..##"
        val result = day10b(map)

        assertEquals(802, result)
    }

    @Test
    fun day10aTest() {
        val map = File("inputDay10").inputStream().bufferedReader().use { it.readText() }
        val result = day10a(map)

        assertEquals(214, result)
    }


    @Test
    fun day10bTest() {
        val map = File("inputDay10").inputStream().bufferedReader().use { it.readText() }
        val result = day10b(map)

        assertEquals(502, result)
    }

    @Test
    fun day10aExampleOne() {
        val map = ".#..#\n" +
                ".....\n" +
                "#####\n" +
                "....#\n" +
                "...##"
        val result = day10a(map)

        assertEquals(8, result)
    }

    @Test
    fun day10aExampleTwo() {
        val map = "......#.#.\n" +
                "#..#.#....\n" +
                "..#######.\n" +
                ".#.#.###..\n" +
                ".#..#.....\n" +
                "..#....#.#\n" +
                "#..#....#.\n" +
                ".##.#..###\n" +
                "##...#..#.\n" +
                ".#....####"
        val result = day10a(map)

        assertEquals(33, result)
    }

    @Test
    fun day10aExampleThree() {
        val map = "#.#...#.#.\n" +
                ".###....#.\n" +
                ".#....#...\n" +
                "##.#.#.#.#\n" +
                "....#.#.#.\n" +
                ".##..###.#\n" +
                "..#...##..\n" +
                "..##....##\n" +
                "......#...\n" +
                ".####.###."
        val result = day10a(map)

        assertEquals(35, result)
    }

    @Test
    fun day10aExampleFour() {
        val map = ".#..#..###\n" +
                "####.###.#\n" +
                "....###.#.\n" +
                "..###.##.#\n" +
                "##.##.#.#.\n" +
                "....###..#\n" +
                "..#.#..#.#\n" +
                "#..#.#.###\n" +
                ".##...##.#\n" +
                ".....#.#.."
        val result = day10a(map)

        assertEquals(41, result)
    }

    @Test
    fun day10aExampleFive() {
        val map = ".#..##.###...#######\n" +
                "##.############..##.\n" +
                ".#.######.########.#\n" +
                ".###.#######.####.#.\n" +
                "#####.##.#.##.###.##\n" +
                "..#####..#.#########\n" +
                "####################\n" +
                "#.####....###.#.#.##\n" +
                "##.#################\n" +
                "#####.##.###..####..\n" +
                "..######..##.#######\n" +
                "####.##.####...##..#\n" +
                ".#####..#.######.###\n" +
                "##...#.##########...\n" +
                "#.##########.#######\n" +
                ".####.#.###.###.#.##\n" +
                "....##.##.###..#####\n" +
                ".#.#.###########.###\n" +
                "#.#.#.#####.####.###\n" +
                "###.##.####.##.#..##"
        val result = day10a(map)

        assertEquals(210, result)
    }


    @Test
    fun parseMap() {
        val map = ".#.\n" +
                "..#\n" +
                "##."
        val asteroidMap = parseMap(map)

        assertEquals(listOf(Point(1, 0), Point(2, 1), Point(0, 2), Point(1, 2)), asteroidMap.asteroidPositions)
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
    fun getAllPointsHorizontalOrVerticalTest() {

        var horizontalPoints = getAllPointsHorizontalOrVertical(Point(0, 0), Point(2, 0))
        assertEquals(listOf(Point(0, 0), Point(1, 0), Point(2, 0)), horizontalPoints)

        horizontalPoints = getAllPointsHorizontalOrVertical(Point(3, 0), Point(1, 0))
        assertEquals(listOf(Point(1, 0), Point(2, 0), Point(3, 0)), horizontalPoints)

        val verticalPoints = getAllPointsHorizontalOrVertical(Point(0, 0), Point(0, 2))
        assertEquals(listOf(Point(0, 0), Point(0, 1), Point(0, 2)), verticalPoints)
    }

    @Test
    fun getManhattanDistanceTest() {
        val (distanceX, distanceY) = getManhattanDistance(Point(2, 2), Point(5, 3))

        assertEquals(3, distanceX)
        assertEquals(1, distanceY)

        val smallestManhattanDistance = smallestManhattanDistance(Point(0, 0), Point(6, 3))
        assertEquals(Pair(2, 1), smallestManhattanDistance)
    }

    @Test
    fun getGreatestCommonDivisorTest() {
        var greatestCommonDivisor = getGreatestCommonDivisor(Pair(8, 12))
        assertEquals(4, greatestCommonDivisor)

        greatestCommonDivisor = getGreatestCommonDivisor(Pair(2, 3))
        assertEquals(1, greatestCommonDivisor)

        greatestCommonDivisor = getGreatestCommonDivisor(Pair(6, 9))
        assertEquals(3, greatestCommonDivisor)
    }


    @Test
    fun getAllPointInBetween() {

        var diagonalPoints = getAllPointsInBetween(Point(0, 0), Point(6, 3))
        assertEquals(listOf(Point(2, 1), Point(4, 2), Point(6, 3)), diagonalPoints)

        diagonalPoints = getAllPointsInBetween(Point(4, 4), Point(2, 8))
        assertEquals(listOf(Point(3, 6), Point(4, 4)), diagonalPoints)

        diagonalPoints = getAllPointsInBetween(Point(-1, -2), Point(2, 7))
        assertEquals(listOf(Point(0, 1), Point(1, 4), Point(2, 7)), diagonalPoints)

    }

}
