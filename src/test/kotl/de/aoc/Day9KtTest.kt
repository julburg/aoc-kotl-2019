package de.aoc

import de.aoc.day9.IntCodeProgram
import org.junit.Test
import java.io.File
import kotlin.test.assertEquals

/**
 * @author  Julia Burgard - burgard@synyx.de
 */
class Day9KtTest {

    @Test
    fun day9a() {
        val codeList = File("inputday9").inputStream().bufferedReader().use { it.readText() }.split(",").map(String::toInt).toIntArray()

        val incodeProgram = IntCodeProgram(codeList, arrayListOf(1))
        val outputValue = incodeProgram.run();

        assertEquals(0, outputValue.values.first())
    }

    @Test
    fun day9aExampleOne() {
        val codeList = "109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99".split(",").map(String::toInt).toIntArray()

        val incodeProgram = IntCodeProgram(codeList, arrayListOf(1))
        val outputValue = incodeProgram.run();

        assertEquals(arrayListOf(109, 1, 204, -1, 1001, 100, 1, 100, 1008, 100, 16, 101, 1006, 101, 0, 99), outputValue.values)
    }

    @Test
    fun day9aExampleTwo() {
        val codeList = "1102,34915192,34915192,7,4,7,99,0".split(",").map(String::toInt).toIntArray()

        val incodeProgram = IntCodeProgram(codeList, arrayListOf(1))
        val outputValue = incodeProgram.run();

        assertEquals(16, outputValue.values.first().toString().length)
    }

    @Test
    fun day9aExampleThree() {
        val codeList = "104,1125899906842624,99".split(",").map(String::toInt).toIntArray()

        val incodeProgram = IntCodeProgram(codeList, arrayListOf(1))
        val outputValue = incodeProgram.run();

        assertEquals(1125899906842624, outputValue.values.first().toLong())
    }
}