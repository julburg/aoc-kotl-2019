package de.aoc

import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File
import java.util.*

/**
 * @author Julia Burgard - burgard@synyx.de
 */
class Day2KtTest {

    @Test
    fun day2aExamples() {

        var input = "1,0,0,0,99";
        var result = day2a(input)
        assertTrue(result.asList().equals(Arrays.asList(2, 0, 0, 0, 99)))

        input = "2,3,0,3,99";
        result = day2a(input)
        assertTrue(result.asList().equals(Arrays.asList(2, 3, 0, 6, 99)))

        input = "2,4,4,5,99,0";
        result = day2a(input)
        assertTrue(result.asList().equals(Arrays.asList(2,4,4,5,99,9801)))

        input = "1,1,1,4,99,5,6,0,99";
        result = day2a(input)
        assertTrue(result.asList().equals(Arrays.asList(30,1,1,4,2,5,6,0,99)))
    }

    @Test
    fun day1a() {
        val input = File("inputday2").inputStream().bufferedReader().use { it.readText() }
        val result = day2a(input)

        assertTrue(result == Arrays.asList(2).toIntArray())
    }


}