package de.aoc

import de.aoc.day5.IntCodeProgram
import org.junit.Test
import java.io.File

/**
 * @author  Julia Burgard - burgard@synyx.de
 */
class Day5KtTest {

    @Test
    fun day5() {
        val input = File("inputday5").inputStream().bufferedReader().use { it.readText() }

        val intCodeProgram = IntCodeProgram(input.split(",").map(String::toInt).toIntArray())
        intCodeProgram.run();
    }

}