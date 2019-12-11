package de.aoc

import de.aoc.Colour.WHITE
import de.aoc.RobotDirection.LEFT
import de.aoc.RobotDirection.UP
import org.junit.Test
import java.awt.Point
import java.io.File
import kotlin.test.assertEquals

/**
 * @author  Julia Burgard - burgard@synyx.de
 */
class Day11KtTest {


    @Test
    fun day11aTest() {
        val input = File("inputday11").inputStream().bufferedReader().use { it.readText() }
        val toMutableList = input.split(",").map(String::toLong).toMutableList()
        toMutableList.addAll((0..10000.toLong()).map { 0L })
        val codeList = toMutableList.toLongArray()

        val robot = day11(codeList)
        assertEquals(0, robot.allVisitedFields.size)
    }
    @Test
    fun day11bTest() {
        val input = File("inputday11").inputStream().bufferedReader().use { it.readText() }
        val toMutableList = input.split(",").map(String::toLong).toMutableList()
        toMutableList.addAll((0..10000.toLong()).map { 0L })
        val codeList = toMutableList.toLongArray()

        val robot = day11(codeList)
        robot.print()
        println() //ABCLFUHJ
        robot.print2()

    }

    @Test
    fun moveRobot() {
        val robot = Robot(UP, Point(1, 1))
        robot.move(0, 1)

        assertEquals(robot.facingDirection, LEFT)
        assertEquals(robot.currentPoint, Point(0, 1))
        assertEquals(robot.allVisitedFields, hashMapOf(Pair(Point(1, 1), WHITE)))

        robot.move(1, 0)

        assertEquals(robot.facingDirection, UP)
        assertEquals(robot.currentPoint, Point(0, 2))
        assertEquals(robot.allVisitedFields, hashMapOf(Pair(Point(1, 1), WHITE), Pair(Point(0, 1), Colour.BLACK)))
    }

}
