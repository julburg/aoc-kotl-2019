package de.aoc

import org.junit.Test
import kotlin.test.assertEquals

/**
 * @author  Julia Burgard - burgard@synyx.de
 */
class Day12KtTest {


    @Test
     fun day12aTestExampleOne() {
        val moons = "<x=-1, y=0, z=2>\n" +
                "<x=2, y=-10, z=-7>\n" +
                "<x=4, y=-8, z=8>\n" +
                "<x=3, y=5, z=-1>";

        val totalEnergy = day12a(moons, 10)

        assertEquals(179, totalEnergy)
    }

    @Test
     fun day12aTestExampleTwo() {
        val moons = "<x=-8, y=-10, z=0>\n" +
                "<x=5, y=5, z=10>\n" +
                "<x=2, y=-7, z=3>\n" +
                "<x=9, y=-8, z=-3>";

        val totalEnergy = day12a(moons, 100)

        assertEquals(1940, totalEnergy)
    }


    @Test
     fun day12aTest() {
        val moons = "<x=-13, y=14, z=-7>\n" +
                "<x=-18, y=9, z=0>\n" +
                "<x=0, y=-3, z=-3>\n" +
                "<x=-15, y=3, z=-13>";

        val totalEnergy = day12a(moons, 1000)

        assertEquals(7138, totalEnergy)
    }

    @Test
     fun day12bTestExampleOne() {
        val moons = "<x=-1, y=0, z=2>\n" +
                "<x=2, y=-10, z=-7>\n" +
                "<x=4, y=-8, z=8>\n" +
                "<x=3, y=5, z=-1>";

        val steps = day12b(moons)

        assertEquals(2772, steps)
    }

    @Test
    fun day12bTestExampleTwo() {
        val moons = "<x=-8, y=-10, z=0>\n" +
                "<x=5, y=5, z=10>\n" +
                "<x=2, y=-7, z=3>\n" +
                "<x=9, y=-8, z=-3>";

        val steps = day12b(moons)

        assertEquals(4686774924, steps)
    }


    @Test
     fun day12bTest() {
        val moons = "<x=-13, y=14, z=-7>\n" +
                "<x=-18, y=9, z=0>\n" +
                "<x=0, y=-3, z=-3>\n" +
                "<x=-15, y=3, z=-13>";

        val steps = day12b(moons)

        assertEquals(0, steps)
    }


    @Test
    fun parseMoonDataTest() {
        val moonData = "<x=-13, y=14, z=-7>\n" +
                "<x=-18, y=9, z=0>\n" +
                "<x=0, y=-3, z=-3>\n" +
                "<x=-15, y=3, z=-13>";

        val moons = parseMoonData(moonData);
        assertEquals(moons.get(0).xPosition, -13)
        assertEquals(moons.get(0).yPosition, 14)
        assertEquals(moons.get(0).zPosition, -7)
    }

    @Test
    fun calcVelocity() {
        val moonsData = "<x=-1, y=0, z=2>\n" +
                "<x=2, y=-10, z=-7>\n" +
                "<x=4, y=-8, z=8>\n" +
                "<x=3, y=5, z=-1>";

        val moons = parseMoonData(moonsData)
        val moons1 = Moons(moons.get(0), moons.get(1), moons.get(2), moons.get(3))
        moons1.apply()
        val (moon1, moon2, moon3, moon4) = moons1

        assertEquals(3, moon1.xVelocity)
        assertEquals(-1, moon1.yVelocity)
        assertEquals(-1, moon1.zVelocity)
        assertEquals(1, moon2.xVelocity)
        assertEquals(3, moon2.yVelocity)
        assertEquals(3, moon2.zVelocity)
        assertEquals(-3, moon3.xVelocity)
        assertEquals(1, moon3.yVelocity)
        assertEquals(-3, moon3.zVelocity)
        assertEquals (-1, moon4.xVelocity)
        assertEquals(-3, moon4.yVelocity)
        assertEquals(1, moon4.zVelocity)

        assertEquals(2, moon1.xPosition)
        assertEquals(-1, moon1.yPosition)
        assertEquals(1, moon1.zPosition)
        assertEquals(3, moon2.xPosition)
        assertEquals(-7, moon2.yPosition)
        assertEquals(-4, moon2.zPosition)
        assertEquals(1, moon3.xPosition)
        assertEquals(-7, moon3.yPosition)
        assertEquals(5, moon3.zPosition)
        assertEquals (2, moon4.xPosition)
        assertEquals(2, moon4.yPosition)
        assertEquals(0, moon4.zPosition)

    }

}


