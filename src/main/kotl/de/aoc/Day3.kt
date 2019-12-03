package de.aoc

import kotlin.math.absoluteValue


fun day3a(input: String): Int {
    val pathes = input.split("\n");
    val firstPath = pathes.get(0).split(",").map { PathElement(Direction.valueOf(it.substring(0, 1)), it.substring(1).toInt()) }
    val secondPath = pathes.get(1).split(",").map { PathElement(Direction.valueOf(it.substring(0, 1)), it.substring(1).toInt()) }

    val coordinatesFirstPath = getCoordinates(firstPath)
    val coordinatesSecondPath = getCoordinates(secondPath)
    var intersections = coordinatesFirstPath.intersect(coordinatesSecondPath).toSet();

    intersections = intersections.minus(Pair(0, 0))

    val min = intersections.map { it.first.absoluteValue + it.second.absoluteValue }.min()
    println(intersections)
    return min!!
}

fun day3b(input: String): Int {
    val pathes = input.split("\n");
    val firstPath = pathes.get(0).split(",").map { PathElement(Direction.valueOf(it.substring(0, 1)), it.substring(1).toInt()) }
    val secondPath = pathes.get(1).split(",").map { PathElement(Direction.valueOf(it.substring(0, 1)), it.substring(1).toInt()) }

    val coordinatesFirstPath = getCoordinates(firstPath)
    val coordinatesSecondPath = getCoordinates(secondPath)
    var intersections = coordinatesFirstPath.intersect(coordinatesSecondPath).toSet();
    intersections = intersections.minus(Pair(0, 0))

    val firstIntersectionFirstPath = getFirstIntersection(coordinatesFirstPath,intersections)
    val firstIntersectionSecondPath = getFirstIntersection(coordinatesSecondPath,intersections)

    val coordinatesFirstPathWithoutCircle = withoutCircles(coordinatesFirstPath);
    val coordinatesSecondPathWithoutCircle = withoutCircles(coordinatesSecondPath);
    val indexOfFirstPath = coordinatesFirstPath.indexOf(firstIntersectionFirstPath);
    val indexOfSecondPath = coordinatesSecondPath.indexOf(firstIntersectionSecondPath);
    println(indexOfFirstPath +  indexOfSecondPath);

    return indexOfFirstPath + indexOfSecondPath
}

fun getFirstIntersection(coordinatesFirstPath: List<Pair<Int, Int>>, intersections: Set<Pair<Int, Int>>): Pair<Int,Int>{
    val indexOfFirst = intersections.map { coordinatesFirstPath.indexOf(it) }.min()!!
    return coordinatesFirstPath.get(indexOfFirst);
}

fun withoutCircles(coordinates: List<Pair<Int, Int>>): List<Pair<Int, Int>> {
    val duplicates = coordinates.groupBy { it }.filter { it.value.size > 1 }.keys.toSet();
    var withoutCircles = coordinates.toMutableList();
    for (duplicate in duplicates) {
        val firstIndexOf = withoutCircles.indexOf(duplicate);
        val lastIndexOf = withoutCircles.lastIndexOf(duplicate);
        withoutCircles = withoutCircles.subtract(withoutCircles.subList(firstIndexOf, lastIndexOf + 1)).toMutableList();
    }
    return withoutCircles
}

private fun getCoordinates(firstPath: List<PathElement>): List<Pair<Int, Int>> {
    var coordinates = Pair(0, 0)
    val points = ArrayList<Pair<Int, Int>>();

    for (pathElement in firstPath) {
        points.addAll(pathElement.getPoints(coordinates));
        coordinates = pathElement.getNewCoordinates(coordinates);
    }
    return points
}

class PathElement(direction: Direction, count: Int) {
    var direction: Direction = direction
    var steps: Int = count


    fun getPoints(startingPoint: Pair<Int, Int>): List<Pair<Int, Int>> {
        return direction.calculatePoints(startingPoint, steps)
    }

    fun getNewCoordinates(startingPoint: Pair<Int, Int>): Pair<Int, Int> {

        return direction.newCoordinates(startingPoint, steps)
    }

    override fun toString(): String {
        return "PathElement(direction='$direction', count=$steps)"
    }

}

enum class Direction {
    R {
        override fun newCoordinates(startpoint: Pair<Int, Int>, steps: Int): Pair<Int, Int> {
            return Pair(startpoint.first + steps, startpoint.second)
        }

        override fun calculatePoints(startpoint: Pair<Int, Int>, steps: Int): ArrayList<Pair<Int, Int>> {
            val points = ArrayList<Pair<Int, Int>>()
            for (xCoordinate in startpoint.first until startpoint.first + steps) {
                points.add(Pair(xCoordinate, startpoint.second))
            }
            return points;
        }
    },

    L {
        override fun newCoordinates(startpoint: Pair<Int, Int>, steps: Int): Pair<Int, Int> {
            return Pair(startpoint.first - steps, startpoint.second)
        }

        override fun calculatePoints(startpoint: Pair<Int, Int>, steps: Int): ArrayList<Pair<Int, Int>> {
            val points = ArrayList<Pair<Int, Int>>()
            for (xCoordinate in (startpoint.first - steps-1)..startpoint.first ) {
                points.add(Pair(xCoordinate, startpoint.second))
            }
            points.reverse()
            return points;
        }
    },

    U {
        override fun newCoordinates(startpoint: Pair<Int, Int>, steps: Int): Pair<Int, Int> {
            return Pair(startpoint.first, startpoint.second + steps)
        }

        override fun calculatePoints(startpoint: Pair<Int, Int>, steps: Int): ArrayList<Pair<Int, Int>> {
            val points = ArrayList<Pair<Int, Int>>()
            for (yCoordinate in startpoint.second until startpoint.second + steps) {
                points.add(Pair(startpoint.first, yCoordinate))
            }
            return points; }
    },

    D {
        override fun newCoordinates(startpoint: Pair<Int, Int>, steps: Int): Pair<Int, Int> {
            return Pair(startpoint.first, startpoint.second - steps)
        }

        override fun calculatePoints(startpoint: Pair<Int, Int>, steps: Int): ArrayList<Pair<Int, Int>> {
            val points = ArrayList<Pair<Int, Int>>()
            for (yCoordinate in (startpoint.second - steps-1)..startpoint.second) {
                points.add(Pair(startpoint.first, yCoordinate))
            }
            points.reverse()
            return points; }
    };

    abstract fun calculatePoints(startpoint: Pair<Int, Int>, steps: Int): ArrayList<Pair<Int, Int>>

    abstract fun newCoordinates(startpoint: Pair<Int, Int>, steps: Int): Pair<Int, Int>
}