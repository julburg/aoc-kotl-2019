package de.aoc

import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * @author Julia Burgard - burgard@synyx.de
 */
class Day4KtTest {

    @Test
    fun day4aExamples() {

        val result = day4a(145852, 616942)
        assertEquals(1767, result)

    }

    @Test
    fun day4bExamples() {

        assertFalse { 123444.isCorrectb() }
        assertFalse { 145852.isCorrectb() }
        assertFalse { 123331.isCorrectb() }
        assertFalse { 333221.isCorrectb() }
        assertTrue { 123355.isCorrectb() }

        assertTrue { 112233.isCorrectb() }
        assertTrue { 111122.isCorrectb() }

        val result = day4b(145852, 616942)
        assertEquals(1192, result)

    }


}