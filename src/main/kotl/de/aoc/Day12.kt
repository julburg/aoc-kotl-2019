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
        if (stepCount == 1000000000L) {
            throw Exception("huhu")
        }
    }
    return stepCount
}

data class Moons(val moon1: Moon, val moon2: Moon, val moon3: Moon, val moon4: Moon) {
    var values = HashMap<Vector, Vector>();

    fun apply() {

        val vectorX = Vector(moon1.xPosition, moon2.xPosition, moon3.xPosition, moon4.xPosition)
        var resultVectorX: Vector? = null
        if (values.containsKey(vectorX)) {
            resultVectorX = values.get(vectorX)!!
        } else {
            resultVectorX = MoonMatrix(vectorX).resultVector
        }
        moon1.xVelocity = moon1.xVelocity + resultVectorX.moon1Pos
        moon2.xVelocity = moon2.xVelocity + resultVectorX.moon2Pos
        moon3.xVelocity = moon3.xVelocity + resultVectorX.moon3Pos
        moon4.xVelocity = moon4.xVelocity + resultVectorX.moon4Pos


        val vectorY = Vector(moon1.yPosition, moon2.yPosition, moon3.yPosition, moon4.yPosition)
        var resultVectorY: Vector? = null
        if (values.containsKey(vectorY)) {
            resultVectorY = values.get(vectorY)!!
        } else {
            resultVectorY = MoonMatrix(vectorY).resultVector
        }
        moon1.yVelocity = moon1.yVelocity + resultVectorY.moon1Pos
        moon2.yVelocity = moon2.yVelocity + resultVectorY.moon2Pos
        moon3.yVelocity = moon3.yVelocity + resultVectorY.moon3Pos
        moon4.yVelocity = moon4.yVelocity + resultVectorY.moon4Pos

        val vectorZ = Vector(moon1.zPosition, moon2.zPosition, moon3.zPosition, moon4.zPosition)
        var resultVectorZ: Vector? = null
        if (values.containsKey(vectorZ)) {
            resultVectorZ = values.get(vectorZ)!!
        } else {
            resultVectorZ = MoonMatrix(vectorZ).resultVector
        }
        moon1.zVelocity = moon1.zVelocity + resultVectorZ.moon1Pos
        moon2.zVelocity = moon2.zVelocity + resultVectorZ.moon2Pos
        moon3.zVelocity = moon3.zVelocity + resultVectorZ.moon3Pos
        moon4.zVelocity = moon4.zVelocity + resultVectorZ.moon4Pos

        values.put(vectorX, resultVectorX)
        values.put(vectorY, resultVectorY)
        values.put(vectorZ, resultVectorZ)

        moon1.applyVelocity()
        moon2.applyVelocity()
        moon3.applyVelocity()
        moon4.applyVelocity()
    }


    fun copyOf(): Moons {
        return Moons(moon1.copyOf(), moon2.copyOf(), moon3.copyOf(), moon4.copyOf())
    }
}

public data class Vector(var moon1Pos: Int, var moon2Pos: Int, var moon3Pos: Int, var moon4Pos: Int) {

}

//-3
class MoonMatrix(vector: Vector) {

    var oneTwo = divPosition(vector.moon1Pos, vector.moon2Pos)
    var oneThree = divPosition(vector.moon1Pos, vector.moon3Pos)
    var oneFour = divPosition(vector.moon1Pos, vector.moon4Pos)

    var twoOne = oneTwo * -1
    var twoThree = divPosition(vector.moon2Pos, vector.moon3Pos)
    var twoFour = divPosition(vector.moon2Pos, vector.moon4Pos)

    var threeOne = oneThree * -1
    var threeTwo = twoThree * -1
    var threeFour = divPosition(vector.moon3Pos, vector.moon4Pos)

    var fourOne = oneFour * -1
    var fourTwo = twoFour * -1
    var fourThree = threeFour * -1

    var resultVector = Vector(oneTwo + oneThree + oneFour, twoOne + twoThree + twoFour, threeOne + threeTwo + threeFour, fourOne + fourTwo + fourThree)


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

    fun calcVelocity(moon1: Moon, moon2: Moon, moon3: Moon) {
        this.xVelocity = this.xVelocity + divXPosition(moon1) + divXPosition(moon2) + divXPosition(moon3)
        this.yVelocity = this.yVelocity + divYPosition(moon1) + divYPosition(moon2) + divYPosition(moon3)
        this.zVelocity = this.zVelocity + divZPosition(moon1) + divZPosition(moon2) + divZPosition(moon3)
    }

    fun calcVelocity(moon2: Moon, moon3: Moon) {
        this.xVelocity = this.xVelocity + divXPosition(moon2) + divXPosition(moon3)
        this.yVelocity = this.yVelocity + divYPosition(moon2) + divYPosition(moon3)
        this.zVelocity = this.zVelocity + divZPosition(moon2) + divZPosition(moon3)
    }

    fun calcVelocity(moon3: Moon) {
        this.xVelocity = this.xVelocity + divXPosition(moon3)
        this.yVelocity = this.yVelocity + divYPosition(moon3)
        this.zVelocity = this.zVelocity + divZPosition(moon3)
    }

    private fun divXPosition(moon1: Moon) = (this.xPosition - moon1.xPosition).sign * -1
    private fun divYPosition(moon1: Moon) = (this.yPosition - moon1.yPosition).sign * -1
    private fun divZPosition(moon: Moon) = (this.zPosition - moon.zPosition).sign * -1


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
