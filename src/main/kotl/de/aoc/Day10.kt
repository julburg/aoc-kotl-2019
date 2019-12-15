package de.aoc

import java.awt.Point


fun day10a(map: String): Int {
    val seenPositions = getSeenPositions(map)
    return seenPositions.values.map { it.size }.max()!!;
}

fun day10b(map: String): Int {
    val seenPositions = getSeenPositions(map)
    val monitoringStation = seenPositions.maxBy { it.value.size }

    val pointsSeenByStation = monitoringStation!!.value
    val stationPosition = monitoringStation.key
    println("Station: " + stationPosition)
    val sortedPositionsByAngle = pointsSeenByStation.sortedBy { calcRotationAngleInDegrees(stationPosition,it) }

    val twoHundrethPosition = sortedPositionsByAngle.get(199)
    return twoHundrethPosition.x * 100+ twoHundrethPosition.y
}

//copied from stackoverflow
fun calcRotationAngleInDegrees(centerPt: Point, targetPt: Point): Double {
    var theta = Math.atan2((targetPt.y - centerPt.y).toDouble(), (targetPt.x - centerPt.x).toDouble())
    theta += Math.PI / 2.0
    var angle = Math.toDegrees(theta)
    if (angle < 0) {
        angle += 360.0
    }

    return angle
}

private fun getSeenPositions(map: String): HashMap<Point, Set<Point>> {
    val starMap = parseMap(map)
    val seenPositions = HashMap<Point, Set<Point>>()
    for (starPoint in starMap.asteroidPositions) {
        seenPositions.put(starPoint, starMap.seenPositions(starPoint))
    }
    return seenPositions
}

data class AsteroidMap(val asteroidPositions: List<Point>) {

    fun getPositionsInBetween(starPoint: Point, pointToCheck: Point): List<Point> {
        if (isOnSameVerticalHorizontal(starPoint, pointToCheck)) {
            return getAllPointsHorizontalOrVertical(starPoint, pointToCheck)
        }

        return getAllPointsInBetween(starPoint, pointToCheck)
    }

    fun seenPositions(starPoint: Point): Set<Point> {
        val seenPositions = HashSet<Point>()
        for (positionToCheck in asteroidPositions) {
            if (starPoint != positionToCheck) {
                var positionsInBetween = getPositionsInBetween(starPoint, positionToCheck)
                positionsInBetween = positionsInBetween.minus(starPoint)
                positionsInBetween = positionsInBetween.minus(positionToCheck)
                val positionInBetweenWithAsteroids = positionsInBetween.filter { asteroidPositions.contains(it) }
                if (positionInBetweenWithAsteroids.isEmpty()) {
                    seenPositions.add(positionToCheck)
                }
            }
        }
        return seenPositions
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


fun getAllPointsHorizontalOrVertical(point1: Point, point2: Point): List<Point> {
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


fun getManhattanDistance(point1: Point, point2: Point): Pair<Int, Int> {
    return Pair(Math.abs(point1.x - point2.x), Math.abs(point1.y - point2.y))
}

fun getGreatestCommonDivisor(distance: Pair<Int, Int>): Int {
    var kgt = Math.max(distance.first, distance.second)
    while (true) {
        if (distance.first % kgt == 0 && distance.second % kgt == 0) {
            return kgt
        }
        --kgt
    }
}


fun getAllPointsInBetween(point1: Point, point2: Point): List<Point> {
    val manhattanDistance = smallestManhattanDistance(point1, point2)
    val pointsInBetween = ArrayList<Point>()
    if (rightBottom(point1, point2)) {
        var nextPoint = point1
        while (nextPoint != point2) {
            nextPoint = Point(nextPoint.x + manhattanDistance.first, nextPoint.y + manhattanDistance.second)
            pointsInBetween.add(nextPoint)
        }
        return pointsInBetween
    }
    if (leftUp(point1, point2)) {
        var nextPoint = point2
        while (nextPoint != point1) {
            nextPoint = Point(nextPoint.x + manhattanDistance.first, nextPoint.y + manhattanDistance.second)
            pointsInBetween.add(nextPoint)
        }
        return pointsInBetween
    }
    if (rightUp(point1, point2)) {
        var nextPoint = point1
        while (nextPoint != point2) {
            nextPoint = Point(nextPoint.x + manhattanDistance.first, nextPoint.y - manhattanDistance.second)
            pointsInBetween.add(nextPoint)
        }
        return pointsInBetween
    }
    if (leftBottom(point1, point2)) {
        var nextPoint = point2
        while (nextPoint != point1) {
            nextPoint = Point(nextPoint.x + manhattanDistance.first, nextPoint.y - manhattanDistance.second)
            pointsInBetween.add(nextPoint)
        }
        return pointsInBetween
    }
    throw IllegalArgumentException("Should not happen")
}

fun smallestManhattanDistance(point1: Point, point2: Point): Pair<Int, Int> {
    val manhattanDistance = getManhattanDistance(point1, point2)
    val gcd = getGreatestCommonDivisor(manhattanDistance)
    return Pair(manhattanDistance.first / gcd, manhattanDistance.second / gcd)
}

private fun rightUp(point1: Point, point2: Point) = point1.x < point2.x && point1.y > point2.y
private fun leftBottom(point1: Point, point2: Point) = point1.x > point2.x && point1.y < point2.y
private fun leftUp(point1: Point, point2: Point) = point1.x > point2.x && point1.y > point2.y
private fun rightBottom(point1: Point, point2: Point) = point1.x < point2.x && point1.y < point2.y


private fun rightUpEquals(point1: Point, point2: Point) = point1.x <= point2.x && point1.y > point2.y
private fun rightBottomEquals(point1: Point, point2: Point) = point1.x < point2.x && point1.y <= point2.y
private fun leftBottomEquals(point1: Point, point2: Point) = point1.x >= point2.x && point1.y < point2.y
private fun leftUpEquals(point1: Point, point2: Point) = point1.x > point2.x && point1.y >= point2.y


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
