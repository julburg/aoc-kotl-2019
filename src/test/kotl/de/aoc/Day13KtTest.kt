package de.aoc

import org.junit.Test
import java.io.File
import kotlin.test.assertEquals

/**
 * @author  Julia Burgard - burgard@synyx.de
 */
class Day13KtTest {


    @Test
    fun day13a() {
        val input = File("inputDay13").inputStream().bufferedReader().use { it.readText() }
        val toMutableList = input.split(",").map(String::toLong).toMutableList()
        toMutableList.addAll((0..10000.toLong()).map { 0L })
        val codeList = toMutableList.toLongArray()

        val blockTileCount = day13a(codeList)

        assertEquals(335, blockTileCount)
    }


}


