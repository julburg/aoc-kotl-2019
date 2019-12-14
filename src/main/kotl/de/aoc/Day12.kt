package de.aoc

import kotlin.math.sign


fun day12a(moonPositions: String, steps: Int): Int {
    val moonsList = parseMoonData(moonPositions);
    val moons = Moons(moonsList.get(0), moonsList.get(1), moonsList.get(2), moonsList.get(3))

    for (step in 1..steps) {
        moons.apply();
    }

    // return moons.map { it.totalEnergy() }.sum()

    return 0
}

fun day12b(moonPositions: String): Long {
    val moonsList = parseMoonData(moonPositions);

    val moons = Moons(moonsList.get(0), moonsList.get(1), moonsList.get(2), moonsList.get(3))
    val originalMoons = moons.copyOf()

    moons.apply()
    var stepCount = 1L
    while (!moons.equals(originalMoons)) {
        moons.apply()
        stepCount += 1L
    }
    return stepCount
}

data class Moons(val moon1: Moon, val moon2: Moon, val moon3: Moon, val moon4: Moon) {

    fun apply() {

        val resultVectorX = MoonMatrix(moon1.xPosition, moon2.xPosition, moon3.xPosition, moon4.xPosition, moon1.xVelocity, moon2.xVelocity, moon3.xVelocity, moon4.xVelocity).resultVector
        moon1.xVelocity = resultVectorX.moon1Pos
        moon2.xVelocity = resultVectorX.moon2Pos
        moon3.xVelocity = resultVectorX.moon3Pos
        moon4.xVelocity = resultVectorX.moon4Pos


        val resultVectorY = MoonMatrix(moon1.yPosition, moon2.yPosition, moon3.yPosition, moon4.yPosition, moon1.yVelocity, moon2.yVelocity, moon3.yVelocity, moon4.yVelocity).resultVector
        moon1.yVelocity = resultVectorY.moon1Pos
        moon2.yVelocity = resultVectorY.moon2Pos
        moon3.yVelocity = resultVectorY.moon3Pos
        moon4.yVelocity = resultVectorY.moon4Pos

        val resultVectorZ = MoonMatrix(moon1.zPosition, moon2.zPosition, moon3.zPosition, moon4.zPosition, moon1.zVelocity, moon2.zVelocity, moon3.zVelocity, moon4.zVelocity).resultVector
        moon1.zVelocity = resultVectorZ.moon1Pos
        moon2.zVelocity = resultVectorZ.moon2Pos
        moon3.zVelocity = resultVectorZ.moon3Pos
        moon4.zVelocity = resultVectorZ.moon4Pos


        moon1.applyVelocity()
        moon2.applyVelocity()
        moon3.applyVelocity()
        moon4.applyVelocity()
    }


    fun copyOf(): Moons {
        return Moons(moon1.copyOf(), moon2.copyOf(), moon3.copyOf(), moon4.copyOf())
    }
}

data class Vector(var moon1Pos: Int, var moon2Pos: Int, var moon3Pos: Int, var moon4Pos: Int)
//-3
class MoonMatrix(moon1Pos: Int, moon2Pos: Int, moon3Pos: Int, moon4Pos: Int, var moon1Vel: Int, var moon2Vel: Int, var moon3Vel: Int, var moon4Vel: Int) {

    var oneTwo = divPosition(moon1Pos, moon2Pos)
    var oneThree = divPosition(moon1Pos, moon3Pos)
    var oneFour = divPosition(moon1Pos, moon4Pos)

    var twoOne = oneTwo * -1
    var twoThree = divPosition(moon2Pos, moon3Pos)
    var twoFour = divPosition(moon2Pos, moon4Pos)

    var threeOne = oneThree * -1
    var threeTwo = twoThree * -1
    var threeFour = divPosition(moon3Pos, moon4Pos)

    var fourOne = oneFour * -1
    var fourTwo = twoFour * -1
    var fourThree = threeFour * -1

    var resultVector = Vector(moon1Vel + oneTwo + oneThree + oneFour, moon2Vel + twoOne + twoThree + twoFour, moon3Vel + threeOne + threeTwo + threeFour, moon4Vel + fourOne + fourTwo + fourThree)


    private fun divPosition(moon1: Int, moon2: Int): Int {
        return (moon1 - moon2).sign * -1
    }
}

data class Moon(var xPosition: Int, var yPosition: Int, var zPosition: Int, var xVelocity: Int, var yVelocity: Int, var zVelocity: Int) {

    fun code(): String {
        return xPosition.toString() + yPosition.toString() + zPosition.toString() + xVelocity.toString() + yVelocity.toString() + zVelocity.toString()
    }

    fun applyVelocity() {
        xPosition = xPosition + xVelocity
        yPosition = yPosition + yVelocity
        zPosition = zPosition + zVelocity
    }

    fun totalEnergy(): Int {
        return potentialEnergy() * kineticEnergy()
    }

    fun copyOf(): Moon {
        return Moon(xPosition, yPosition, zPosition, xVelocity, yVelocity, zVelocity)
    }

    fun potentialEnergy(): Int {
        return Math.abs(xPosition) + Math.abs(yPosition) + Math.abs(zPosition)
    }

    fun kineticEnergy(): Int {
        return Math.abs(xVelocity) + Math.abs(yVelocity) + Math.abs(zVelocity)
    }
}

fun parseMoonData(moonPositions: String): List<Moon> {
    val moonsSplitted = moonPositions.split("\n")
    val regex = "<x=(-?\\d*), y=(-?\\d*), z=(-?\\d*)>".toRegex()
    val moons = ArrayList<Moon>();
    for (moon in moonsSplitted) {
        val (x, y, z) = regex.matchEntire(moon)!!.destructured
        moons.add(Moon(x.toInt(), y.toInt(), z.toInt(), 0, 0, 0))
    }
    return moons
}
