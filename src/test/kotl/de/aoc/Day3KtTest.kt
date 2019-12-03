package de.aoc

import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File
import kotlin.test.assertEquals

/**
 * @author Julia Burgard - burgard@synyx.de
 */
class Day3KtTest {

    @Test
    fun day3aExamples() {

        var input = "R75,D30,R83,U83,L12,D49,R71,U7,L72\n" +
                "U62,R66,U55,R34,D71,R55,D58,R83";
        var result = day3a(input)
        assertTrue(result.equals(159))

        input = "R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51\n" +
                "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7";
        result = day3a(input)
        assertTrue(result.equals(135))

    }

    @Test
    fun directionEnum() {
        var calculatedPoints = Direction.R.calculatePoints(Pair(0, 0), 1);
        val newCoordinates = Direction.R.newCoordinates(Pair(0, 0), 1);
        assertEquals(newCoordinates, Pair(1, 0))
        assertEquals(listOf(Pair(0, 0)), calculatedPoints)

        calculatedPoints = Direction.L.calculatePoints(Pair(2, 0), 3);
        assertEquals(listOf(Pair(2, 0), Pair(1, 0), Pair(0, 0), Pair(-1, 0)), calculatedPoints)

        calculatedPoints = Direction.U.calculatePoints(Pair(0, 0), 1);
        assertEquals(listOf(Pair(0, 0), Pair(0, 1)), calculatedPoints)

        calculatedPoints = Direction.D.calculatePoints(Pair(0, 0), 1);
        assertEquals(listOf(Pair(0, 0), Pair(0, -1)), calculatedPoints)

    }

    @Test
    fun day3a() {
        val input = File("inputday3").inputStream().bufferedReader().use { it.readText() }
        val result = day3a(input)

        assertEquals(860, result)
    }


    @Test
    fun day3bExamples() {
        // var input = "R1,U5\n" +
        //         "U1,R5";
        // var result = day3b(input)
        // assertTrue(result.equals(4))
//
         var input = "R75,D30,R83,U83,L12,D49,R71,U7,L72\n" +
                 "U62,R66,U55,R34,D71,R55,D58,R83";
         var result = day3b(input)
         assertTrue(result.equals(610))

        input = "R8,U5,L5,D3\n" +
                "U7,R6,D4,L4";
         result = day3b(input)
        assertTrue(result.equals(30))

        input = "R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51\n" +
                "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7";
        result = day3b(input)
        assertTrue(result.equals(410))

    }

    @Test
    fun day3b() {
        val input = File("inputday3").inputStream().bufferedReader().use { it.readText() }
        val result = day3b(input)

        assertEquals(1, result)
    }

    @Test
    fun withoutCircles() {
        val withoutCircles = withoutCircles(arrayListOf(Pair(0, 0), Pair(0, 1), Pair(1, 1), Pair(0, 2), Pair(1, 1), Pair(0, 3)))
        assertEquals(listOf(Pair(0, 0), Pair(0, 1), Pair(0, 3)), withoutCircles.toList())

    }
}