package de.aoc

import org.junit.Test
import java.io.File
import kotlin.test.assertEquals

/**
 * @author  Julia Burgard - burgard@synyx.de
 */
class Day6KtTest {


    @Test
    fun day1aExamples() {

        val map = "COM)B\n" +
                "B)C\n" +
                "C)D\n" +
                "D)E\n" +
                "E)F\n" +
                "B)G\n" +
                "G)H\n" +
                "D)I\n" +
                "E)J\n" +
                "J)K\n" +
                "K)L";

        val result = day6a(map);
        assertEquals(42, result)

    }

    @Test
    fun calculateOrbiCountTest() {
        val map = "COM)B\n" +
                "B)C\n" +
                "C)D\n" +
                "D)E\n" +
                "E)F\n" +
                "B)G\n" +
                "G)H\n" +
                "D)I\n" +
                "E)J\n" +
                "J)K\n" +
                "K)L";

        val orbitElements = map.split("\n").map {
            OrbitElement(it.split(")").get(0), it.split(")").get(1))
        };

        var indirectOrbitCount = calculateIndirectOrbitCount("COM", orbitElements, 0)
        assertEquals(0, indirectOrbitCount)

        indirectOrbitCount = calculateIndirectOrbitCount("L", orbitElements, 0)
        assertEquals(3, indirectOrbitCount)
    }

    @Test
    fun calculateIndirectOrbitsPlanets() {
        val map = "COM)B\n" +
                "B)C\n" +
                "C)D\n" +
                "D)E\n" +
                "E)F\n" +
                "B)G\n" +
                "G)H\n" +
                "D)I\n" +
                "E)J\n" +
                "J)K\n" +
                "K)L";

        val orbitElements = map.split("\n").map {
            OrbitElement(it.split(")").get(0), it.split(")").get(1))
        };

        var orbitPlanets = calculateIndirectOrbitsPlanets("COM", orbitElements, arrayListOf())
        assertEquals(emptyList<String>(), orbitPlanets)

        orbitPlanets = calculateIndirectOrbitsPlanets("D", orbitElements, arrayListOf())
        assertEquals(listOf("C","B","COM"), orbitPlanets)
    }

    @Test
    fun calculateTo() {
        val map = "COM)B\n" +
                "B)C\n" +
                "C)D\n" +
                "D)E\n" +
                "E)F\n" +
                "B)G\n" +
                "G)H\n" +
                "D)I\n" +
                "E)J\n" +
                "J)K\n" +
                "K)L";

        val orbitElements = map.split("\n").map {
            OrbitElement(it.split(")").get(0), it.split(")").get(1))
        };

        val result = calculateTo("I", orbitElements, "H");
        assertEquals(3,result)

    }
    @Test
    fun day6aSolution() {
        val input = File("inputday6").inputStream().bufferedReader().use { it.readText() }

        val result = day6a(input);
        assertEquals(42, result)
    }

    @Test
    fun day6bSolution() {
        val input = File("inputday6").inputStream().bufferedReader().use { it.readText() }

        val result = day6b(input);
        assertEquals(42, result)
    }

}
