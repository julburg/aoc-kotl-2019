package de.aoc

import de.aoc.CardinalDirection.NORTH
import de.aoc.day11.IntCodeProgram
import java.awt.Point


fun day15a(codeList: LongArray): Int {
    return getVisitedAndDeadEndPositions(codeList).visitedPositions.size

}

fun day15b(codeList: LongArray): Int {
    val exploredPositions = getVisitedAndDeadEndPositions(codeList)
    val allPositions = HashMap<Point, Int>()
    allPositions.putAll(exploredPositions.visitedPositions.map { it to 0 }.toMap())
    allPositions.putAll(exploredPositions.deadEndPositions.map { it to 0 }.toMap())

    val monitoringStation = exploredPositions.monitoringStation
    allPositions.put(monitoringStation, 1)

    var minutes=0
    while(allPositions.filter { it.value==0 }.isNotEmpty()){
        minutes += 1
        val positionsWithOxygen = allPositions.filter { it.value == 1 }
        for (positionWithOxygen in positionsWithOxygen) {
            val adjacentPositions = getAdjacentPositions(positionWithOxygen.key).filter { allPositions.contains(it) }
            for (adjacentPosition in adjacentPositions) {
                allPositions.put(adjacentPosition, 1)
            }
        }

    }

    return minutes

}

fun getAdjacentPositions(position: Point): List<Point> {
    return arrayListOf(Point(position.x, position.y + 1), Point(position.x, position.y - 1), Point(position.x + 1, position.y), Point(position.x - 1, position.y))
}


private fun getVisitedAndDeadEndPositions(codeList: LongArray): ExploredPositions {
    val visitedPositions = ArrayList<Point>()
    val deadEndPositions = ArrayList<Point>()
    val walls = ArrayList<Point>()

    var currentDirection = NORTH
    var currentPosition = Point(0, 0)
    visitedPositions.add(currentPosition)
    val intCodeProgram = IntCodeProgram(codeList.copyOf())
    var monitoringPosition = Point(0, 0)
    while (!intCodeProgram.isTerminated) {
        val output = intCodeProgram.run(currentDirection.num).values.first();
        if (output.equals(2L)) {
            monitoringPosition = currentDirection.getNextPosition(currentPosition)
            visitedPositions.add(monitoringPosition)
            break
        }
        if (output.equals(0L)) {
            val cd = currentDirection
            walls.add(currentDirection.getNextPosition(currentPosition));
            currentDirection = getNextDirection(currentDirection, currentPosition, deadEndPositions, visitedPositions, walls)
            println("Wall in direction:" + cd + " next direction: " + currentDirection)

            continue
        }
        if (output.equals(1L)) {
            currentPosition = currentDirection.getNextPosition(currentPosition)
            if (visitedPositions.contains(currentPosition)) {
                println("visited again " + currentPosition)
                val pointsInBetween = getPointsAfter(visitedPositions, currentPosition)
                deadEndPositions.addAll(pointsInBetween)
                visitedPositions.removeAll(pointsInBetween)
            } else {
                visitedPositions.add(currentPosition)
            }
            currentDirection = getNextDirection(currentDirection, currentPosition, deadEndPositions, visitedPositions, walls)
            println("One Step to : " + currentPosition + " with next direction: " + currentDirection)

        }
    }
    return ExploredPositions(visitedPositions, deadEndPositions, monitoringPosition)
}

class ExploredPositions(val visitedPositions: ArrayList<Point>, val deadEndPositions: ArrayList<Point>, val monitoringStation: Point)

fun getNextDirection(currentDirection: CardinalDirection, currentPosition: Point, deadEndPositions: ArrayList<Point>, visitedPositions: ArrayList<Point>, walls: java.util.ArrayList<Point>): CardinalDirection {
    var nextDirection = currentDirection
    for (d in 1..4) {
        nextDirection = nextDirection(nextDirection)
        if (!isADeadEnd(currentPosition, nextDirection, deadEndPositions) && !wall(currentPosition, nextDirection, walls) && !visited(currentPosition, nextDirection, visitedPositions)) {
            return nextDirection
        }
    }
    for (d in 1..4) {
        nextDirection = nextDirection(nextDirection)
        if (!isADeadEnd(currentPosition, nextDirection, deadEndPositions) && !wall(currentPosition, nextDirection, walls)) {
            return nextDirection
        }
    }

    throw Exception("Terminated because we have nowhere to go")
}

fun visited(currentPosition: Point, direction: CardinalDirection, visitedPositions: java.util.ArrayList<Point>): Boolean {
    return visitedPositions.contains(direction.getNextPosition(currentPosition))
}

fun wall(currentPosition: Point, direction: CardinalDirection, walls: java.util.ArrayList<Point>): Boolean {
    return walls.contains(direction.getNextPosition(currentPosition))
}

private fun isADeadEnd(currentPosition: Point, direction: CardinalDirection, deadEndPositions: ArrayList<Point>) =
        deadEndPositions.contains(direction.getNextPosition(currentPosition))

private fun getPointsAfter(visitedPositions: ArrayList<Point>, currentPosition: Point): List<Point> {
    val firstIndexOfPosition = visitedPositions.indexOfFirst { it.equals(currentPosition) }
    return visitedPositions.subList(firstIndexOfPosition + 1, visitedPositions.size)
}


enum class CardinalDirection(val num: Long) {
    NORTH(1L) {
        override fun getNextPosition(position: Point): Point {
            return Point(position.x, position.y + 1)
        }
    },
    EAST(4L) {
        override fun getNextPosition(position: Point): Point {
            return Point(position.x + 1, position.y)
        }
    },
    SOUTH(2L) {
        override fun getNextPosition(position: Point): Point {
            return Point(position.x, position.y - 1)
        }
    },
    WEST(3L) {
        override fun getNextPosition(position: Point): Point {
            return Point(position.x - 1, position.y)
        }
    };


    abstract fun getNextPosition(position: Point): Point
}


fun nextDirection(currentDirection: CardinalDirection): CardinalDirection {
    val nextDirection = (currentDirection.ordinal + 1) % 4
    return CardinalDirection.values().first { it.ordinal == nextDirection }
}

