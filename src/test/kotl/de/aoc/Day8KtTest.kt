package de.aoc

import org.junit.Test
import java.io.File
import kotlin.test.assertEquals

/**
 * @author  Julia Burgard - burgard@synyx.de
 */
class Day8KtTest {

    @Test
    fun day8aExampleOne_divideString() {
        val imageData = "123456789012";
        val divided = parseLayers(imageData, 3 * 2);

        assertEquals(listOf(Layer("123456"), Layer("789012")), divided)
    }

    @Test
    fun day8aExampleOne() {
        val imageData = "123456789012";
        val divided = day8a(3, 2, imageData);

        assertEquals(divided, 1)
    }

    @Test
    fun day8aTest() {
        val imageData = File("inputday8").inputStream().bufferedReader().use { it.readText() }
        val divided = day8a(25, 6, imageData);

        assertEquals(divided, 1)
    }

    @Test
    fun day8bExampleOne() {
        val imageData = "0222112222120002";
        val image = day8b(2, 2, imageData);

        assertEquals(arrayListOf('0', '1', '1', '2'), image)
    }

    @Test
    fun day8bTest() {
        val imageData = File("inputday8").inputStream().bufferedReader().use { it.readText() }
        val image = day8b(25, 6, imageData);
        val joinToString = image.joinToString("");
        image.chunked(25).forEach { println(it.joinToString("")) };
    }


}