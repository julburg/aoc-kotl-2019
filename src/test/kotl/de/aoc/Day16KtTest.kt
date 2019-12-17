package de.aoc

import org.junit.Test
import java.io.File
import kotlin.test.assertEquals

/**
 * @author  Julia Burgard - burgard@synyx.de
 */
class Day16KtTest {
    @Test
    fun day16a() {
        val inputSignal = File("inputday16").inputStream().bufferedReader().use { it.readText() }

        val result = day16a(inputSignal)
        assertEquals(74369033, result)
    }

    @Test
    fun day16aTestExampleOne() {
        val inputSignal = "80871224585914546619083218645595"

        val result = day16a(inputSignal)
        assertEquals(24176176, result)
    }

    @Test
    fun day16aTestExampleTwo() {
        val inputSignal = "19617804207202209144916044189917"

        val result = day16a(inputSignal)
        assertEquals(73745418, result)
    }

    @Test
    fun day16aTestExampleThree() {
        val inputSignal = "69317163492948606335995924319873"

        val result = day16a(inputSignal)
        assertEquals(52432133, result)
    }

    @Test
    fun getPatternTest() {
        var pattern = getPattern(2, 8)
        assertEquals(arrayListOf(0, 1, 1, 0, 0, -1, -1, 0, 0, 1, 1, 0, 0, -1, -1), pattern)

        pattern = getPattern(3, 5)
        assertEquals(arrayListOf(0, 0, 1, 1, 1, 0, 0, 0, -1, -1, -1), pattern)
    }

    @Test
    fun cutNumberTest() {
        var number = cut(-96)
        assertEquals(number, 6)

        number = cut(6)
        assertEquals(number, 6)
    }


}


