package de.aoc

import java.awt.Point


fun day10(map: String): Int {
    val starMap = parseMap(map)
    val hiddenPositions = HashMap<Point, Set<Point>>()
    for (starPoint in starMap.positions) {
        hiddenPositions.put(starPoint, starMap.hiddenPositions(starPoint))
    }

    val pointWithMinHiddenPositions = hiddenPositions.minBy { it.value.size }!!
    return starMap.positions.size - pointWithMinHiddenPositions.value.size;
}

data class AsteroidMap(val positions: List<Point>) {

    fun maxX(): Int {
        return positions.map { it.x }.max()!!
    }

    fun maxY(): Int {
        return positions.map { it.y }.max()!!
    }

    fun getHiddenPositions(starPoint: Point, pointToCheck: Point): List<Point> {
        if (isOnSameVerticalHorizontal(starPoint, pointToCheck)) {
            return getAllPointsHorizontalOrVertical(starPoint, pointToCheck, positions)
        }
        if (isOnSameDiagonal(starPoint, pointToCheck)) {
            return getAllPointsDiagonal(starPoint, pointToCheck, this)
        }

        return getAllPointsInBetween(starPoint, pointToCheck, this)
    }

    fun hiddenPositions(starPoint: Point): Set<Point> {
        val hiddenPositions = HashSet<Point>()
        for (pointToCheck in positions) {
            if (starPoint != pointToCheck) {
                hiddenPositions.addAll(getHiddenPositions(starPoint, pointToCheck).toSet())
            }
        }
        return hiddenPositions
    }
}


fun isOnSameVerticalHorizontal(point1: Point, point2: Point): Boolean {
    if (isOnVertical(point1, point2) || isOnHorizontal(point1, point2)) {
        return true
    }
    return false
}

private fun isOnHorizontal(point1: Point, point2: Point) = point1.y == point2.y
private fun isOnVertical(point1: Point, point2: Point) = point1.x == point2.x

fun isOnSameDiagonal(point1: Point, point2: Point): Boolean {
    if (Math.abs(point1.x - point2.x) == Math.abs(point1.y - point2.y)) {
        return true
    }
    return false
}

fun getAllPointsHorizontalOrVertical(point1: Point, point2: Point, allPoints: List<Point>): List<Point> {
    if (isOnHorizontal(point1, point2)) {
        if (point1.x > point2.x) {
            return (point2.x..point1.x).map { x -> Point(x, point2.y) }
        }
        return (point1.x..point2.x).map { x -> Point(x, point2.y) }
    }
    if (point1.y > point2.y) {
        return (point2.y..point1.y).map { y -> Point(point2.x, y) }
    }
    return (point1.y..point2.y).map { y -> Point(point2.x, y) }
}

fun getAllPointsDiagonal(point1: Point, point2: Point, map: AsteroidMap): List<Point> {
    if (rightBottom(point1, point2)) {
        return (point1.x..point2.x).withIndex().map { (index, x) -> Point(x, point1.y + index) }
    }
    if (leftUp(point1, point2)) {
        return (point2.x..point1.x).withIndex().map { (index, x) -> Point(x, point2.y + index) }
    }
    if (leftBottom(point1, point2)) {
        return (point1.x..point2.x).withIndex().map { (index, x) -> Point(x, point1.y - index) }
    }
    if (rightUp(point1, point2)) {
        return (point2.x..point1.x).withIndex().map { (index, x) -> Point(x, point2.y - index) }
    }
    throw IllegalArgumentException("Should not happen")
}

fun getManhattanDistance(point1: Point, point2: Point): Pair<Int, Int> {
    return Pair(Math.abs(point1.x - point2.x), Math.abs(point1.y - point2.y))
}

fun getLowestCommonDivisor(distance: Pair<Int, Int>):Int {
    var kgt = 2
    while (true) {
        if (distance.first % kgt== 0 && kgt % distance.second % kgt== 0) {
            return kgt
        }
        ++kgt
    }
}

fun getAllPointsInBetween(point1: Point, point2: Point, map: AsteroidMap): List<Point> {
    if (rightBottom(point1, point2)) {
    }
    if (leftUp(point1, point2)) {
    }
    if (rightUp(point1, point2)) {
    }
    if (leftBottom(point1, point2)) {
    }
    throw IllegalArgumentException("Should not happen")
}

private fun rightUp(point1: Point, point2: Point) = point1.x > point2.x && point1.y < point2.y

private fun leftBottom(point1: Point, point2: Point) = point1.x < point2.x && point1.y > point2.y

private fun leftUp(point1: Point, point2: Point) = point1.x > point2.x && point1.y > point2.y

private fun rightBottom(point1: Point, point2: Point) = point1.x < point2.x && point1.y < point2.y


fun parseMap(map: String): AsteroidMap {
    val asteroidMap = ArrayList<Point>()
    val rows = map.split("\n");
    for ((y, row) in rows.withIndex()) {
        val columns = row.toCharArray();
        for ((x, column) in columns.withIndex()) {
            if (column.equals('#')) {
                asteroidMap.add(Point(x, y))
            }
        }
    }
    return AsteroidMap(asteroidMap)
}
