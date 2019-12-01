package de.aoc

import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File

/**
 * @author Julia Burgard - burgard@synyx.de
 */
class Day1KtTest {

    @Test
    fun day1aExamples() {

        var input = "12";
        var result = day1a(input)
        assertTrue(result == 2)

        input = "14";
        result = day1a(input)
        assertTrue(result == 2)

        input = "1969";
        result = day1a(input)
        assertTrue(result == 654)

        input = "100756";
        result = day1a(input)
        assertTrue(result == 33583)
    }

    @Test
    fun day1a() {
        val input = File("inputday1").inputStream().bufferedReader().use { it.readText() }
        val result = day1a(input)

        assertTrue(result == 3226488)
    }

    @Test
    fun day1bExamples() {

        var input = "12";
        var result = day1b(input)
        assertTrue(result == 2)

        input = "1969";
        result = day1b(input)
        assertTrue(result == 966)

        input = "100756";
        result = day1b(input)
        assertTrue(result == 50346)

    }

    @Test
    fun day1b() {
        val input = File("inputday1").inputStream().bufferedReader().use { it.readText() }
        val result = day1b(input)

        assertTrue(result == 4836845)
    }


}