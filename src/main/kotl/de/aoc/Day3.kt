package de.aoc

import kotlin.math.absoluteValue


fun day3a(input: String): Int {
    val pathes = input.split("\n");
    val firstPath = pathes.get(0).split(",").map { PathElement(Direction.valueOf(it.substring(0, 1)), it.substring(1).toInt()) }
    val secondPath = pathes.get(1).split(",").map { PathElement(Direction.valueOf(it.substring(0, 1)), it.substring(1).toInt()) }

    val coordinatesFirstPath = getCoordinates(firstPath)
    val coordinatesSecondPath = getCoordinates(secondPath)
    var intersections = coordinatesFirstPath.intersect(coordinatesSecondPath);

    intersections = intersections.minus(Pair(0, 0))

    val min = intersections.map { it.first.absoluteValue + it.second.absoluteValue }.min()
    println(intersections)
    return min!!
}

private fun getCoordinates(firstPath: List<PathElement>): HashSet<Pair<Int, Int>> {
    var coordinates = Pair(0, 0)
    var points = HashSet<Pair<Int, Int>>();

    for (pathElement in firstPath) {
        points.addAll(pathElement.getPoints(coordinates));
        coordinates = pathElement.getNewCoordinates(coordinates);
    }
    return points
}

class PathElement(direction: Direction, count: Int) {
    var direction: Direction = direction
    var steps: Int = count

    fun getPoints(startingPoint: Pair<Int, Int>): Set<Pair<Int, Int>> {
        return direction.calculatePoints(startingPoint, steps).toSet()
    }

    fun getNewCoordinates(startingPoint: Pair<Int, Int>): Pair<Int, Int> {

        return direction.calculatePoints(startingPoint, steps).last()
    }

    override fun toString(): String {
        return "PathElement(direction='$direction', count=$steps)"
    }

}

enum class Direction {
    R {
        override fun calculatePoints(startpoint: Pair<Int, Int>, steps: Int): ArrayList<Pair<Int, Int>> {
            val points = ArrayList<Pair<Int, Int>>()
            for (xCoordinate in startpoint.first..startpoint.first + steps) {
                points.add(Pair(xCoordinate, startpoint.second))
            }
            return points;
        }
    },

    L {
        override fun calculatePoints(startpoint: Pair<Int, Int>, steps: Int): ArrayList<Pair<Int, Int>> {
            val points = ArrayList<Pair<Int, Int>>()
            for (xCoordinate in startpoint.first downTo (startpoint.first - steps)) {
                points.add(Pair(xCoordinate, startpoint.second))
            }
            return points;
        }
    },

    U {
        override fun calculatePoints(startpoint: Pair<Int, Int>, steps: Int): ArrayList<Pair<Int, Int>> {
            val points = ArrayList<Pair<Int, Int>>()
            for (yCoordinate in startpoint.second..startpoint.second + steps) {
                points.add(Pair(startpoint.first, yCoordinate))
            }
            return points; }
    },

    D {
        override fun calculatePoints(startpoint: Pair<Int, Int>, steps: Int): ArrayList<Pair<Int, Int>> {
            val points = ArrayList<Pair<Int, Int>>()
            for (yCoordinate in startpoint.second downTo (startpoint.second - steps)) {
                points.add(Pair(startpoint.first, yCoordinate))
            }
            return points; }
    };

    abstract fun calculatePoints(startpoint: Pair<Int, Int>, steps: Int): ArrayList<Pair<Int, Int>>
}