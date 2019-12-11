package de.aoc

import de.aoc.Colour.BLACK
import de.aoc.Colour.WHITE
import de.aoc.RobotDirection.UP
import de.aoc.day11.IntCodeProgram
import java.awt.Point


fun day11(codeList: LongArray): Robot {

    val robot = Robot(UP, Point(0, 0))
    robot.setInitState()

    val intCodeProgram = IntCodeProgram(codeList.copyOf())
    while (!intCodeProgram.isTerminated) {
        val output = intCodeProgram.run(robot.getColourOfCurrentPoint().key);
        if (intCodeProgram.isTerminated) {

            println()
        }
        val currentPanelColour = output.values.get(0)
        val turn90DegreesDirection = output.values.get(1)
        robot.move(turn90DegreesDirection, currentPanelColour)

    }

    return robot

}


data class Robot(var facingDirection: RobotDirection, var currentPoint: Point) {
    var allVisitedFields = hashMapOf<Point, Colour>()


    fun move(turn90DegreesDirection: Long, colour: Long) {
        facingDirection = facingDirection.turn90Degrees(turn90DegreesDirection)
        val currentColour = getColourBy(colour)
        allVisitedFields.put(currentPoint, currentColour)
        currentPoint = facingDirection.moveOneField(currentPoint)
    }

    fun getColourOfCurrentPoint(): Colour {
        if (allVisitedFields.containsKey(currentPoint)) {
            return allVisitedFields.get(currentPoint)!!
        }
        return BLACK
    }

    fun setInitState() {
        allVisitedFields.put(Point(0, 0), WHITE)
    }

    fun print() {
        val allYValues = allVisitedFields.keys.map { it.y }.sorted().reversed().toSet()
        for (y in allYValues) {
            val allXValues = allVisitedFields.keys.filter { it.y == y }.map { it.x }.sorted().reversed().toSet()
            for (x in allXValues) {
                val point = Point(x, y)
                val colour = allVisitedFields.get(point)!!
                print(colour.printable)
            }
            println()

        }
    }

    fun print2() {
        val allXValues = allVisitedFields.keys.map { it.x }.sorted().reversed().toSet()
        for (x in allXValues) {
            val allYValues = allVisitedFields.keys.filter { it.x == x }.map { it.y }.sorted().reversed().toSet()
            for (y in allYValues) {
                val point = Point(x, y)
                val colour = allVisitedFields.get(point)!!
                print(colour.printable)
            }
            println()

        }
    }

}


enum class Colour(val key: Long, val printable: String) {
    BLACK(0, "."), WHITE(1, "#");

    companion object

}

fun getColourBy(key: Long): Colour {
    when (key) {
        0L -> return BLACK
        1L -> return WHITE
        else -> throw IllegalArgumentException("Colour not parsable.")
    }
}

enum class RobotDirection() {
    UP {
        override fun moveOneField(point: Point): Point = Point(point.x, point.y + 1)
        override fun turnLeft() = LEFT
        override fun turnRight() = RIGHT
    },
    RIGHT {
        override fun moveOneField(point: Point): Point = Point(point.x + 1, point.y)
        override fun turnLeft() = UP
        override fun turnRight() = DOWN
    },
    LEFT {
        override fun moveOneField(point: Point): Point = Point(point.x - 1, point.y)
        override fun turnLeft() = DOWN
        override fun turnRight() = UP
    },
    DOWN {
        override fun moveOneField(point: Point): Point = Point(point.x, point.y - 1)
        override fun turnLeft() = RIGHT
        override fun turnRight() = LEFT
    };


    abstract fun turnLeft(): RobotDirection
    abstract fun turnRight(): RobotDirection
    abstract fun moveOneField(point: Point): Point

    companion object

    fun turn90Degrees(turnKey: Long): RobotDirection {
        if (turnKey == 0L) {
            return turnLeft()
        } else {
            return turnRight()
        }
    }
}