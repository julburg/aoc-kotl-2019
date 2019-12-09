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
        val codeList = getArrayList(File("inputday9").inputStream().bufferedReader().use { it.readText() })

        val incodeProgram = IntCodeProgram(codeList, arrayListOf(1))
        val outputValue = incodeProgram.run();

        assertEquals(arrayListOf(), outputValue.values)
    }

    @Test
    fun day9aExampleOne() {
        val codeList = getArrayList("109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99")

        val incodeProgram = IntCodeProgram(codeList, arrayListOf(1))
        val outputValue = incodeProgram.run();

        assertEquals(arrayListOf(109, 1, 204, -1, 1001, 100, 1, 100, 1008, 100, 16, 101, 1006, 101, 0, 99).map { it.toLong() }, outputValue.values)
    }

    @Test
    fun day9aExampleTwo() {
        val codeList = getArrayList("1102,34915192,34915192,7,4,7,99,0")

        val incodeProgram = IntCodeProgram(codeList, arrayListOf(1))
        val outputValue = incodeProgram.run();

        assertEquals(16, outputValue.values.first().toString().length)
    }

    @Test
    fun day9aExampleThree() {
        val codeList = getArrayList("104,1125899906842624,99")

        val incodeProgram = IntCodeProgram(codeList, arrayListOf(1))
        val outputValue = incodeProgram.run();

        assertEquals(1125899906842624, outputValue.values.first().toLong())
    }

        @Test
    fun day9b() {
        val codeList = getArrayList(File("inputday9").inputStream().bufferedReader().use { it.readText() })

        val incodeProgram = IntCodeProgram(codeList, arrayListOf(2))
        val outputValue = incodeProgram.run();

        assertEquals(arrayListOf(), outputValue.values)
    }


    private fun getArrayList(input: String): LongArray {
        val toMutableList = input.split(",").map(String::toLong).toMutableList()
        toMutableList.addAll((0..10000.toLong()).map { 0L })
        val codeList = toMutableList.toLongArray()
        return codeList
    }
}
