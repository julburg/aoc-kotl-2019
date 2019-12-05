package de.aoc.day5

import de.aoc.day5.IntCodeProgram.OpCode.MULTIPLY
import de.aoc.day5.IntCodeProgram.OpCode.SUM
import org.junit.Test
import kotlin.test.assertEquals

/**
 * @author Julia Burgard - burgard@synyx.de
 */
class InstructionTest {

    @Test
    fun sumInstruction() {

        var sumInstruction = MultiplyAndSumInstruction(intArrayOf(1002, 1, 3, 0), opCode = SUM)
        var codeList = intArrayOf(0, 1, 3, 0)
        sumInstruction.run(codeList,0)

        assertEquals(intArrayOf(4, 1, 3, 0).toList(), codeList.toList())

        sumInstruction = MultiplyAndSumInstruction(intArrayOf(1102, 5, 3, 0), opCode = SUM)
        codeList = intArrayOf(0, 1, 3, 0)
        sumInstruction.run(codeList,0)

        assertEquals(intArrayOf(8, 1, 3, 0).toList(), codeList.toList())

    }

    @Test
    fun multiplyInstruction() {

        var sumInstruction = MultiplyAndSumInstruction(intArrayOf(1002, 1, 2, 0), opCode = MULTIPLY)
        var codeList = intArrayOf(0, 1, 3, 0)
        sumInstruction.run(codeList,0)

        assertEquals(intArrayOf(2, 1, 3, 0).toList(), codeList.toList())

        sumInstruction = MultiplyAndSumInstruction(intArrayOf(1102, 1, 5, 0), opCode = MULTIPLY)
        codeList = intArrayOf(0, 1, 4, 0)
        sumInstruction.run(codeList,0)

        assertEquals(intArrayOf(5, 1, 4, 0).toList(), codeList.toList())

    }

    @Test
    fun getMode() {

        val modeFirstValue = getModeFirstValue(11002)
        assertEquals(modeFirstValue, 0)

        val modeSecondValue = getModeSecondValue(11002)
        assertEquals(modeSecondValue, 1)

        val modeThirdValue = getModeThirdValue(11002)
        assertEquals(modeThirdValue, 1)
    }

    @Test
    fun inputInstruction() {
        val inputInstruction = InputInstruction(intArrayOf(3, 2), SUM)
        val codeList = intArrayOf(1, 2, 3)
        inputInstruction.run(codeList,0)

        assertEquals(intArrayOf(1, 2, 1).toList(), codeList.toList())
    }

    @Test
    fun outputInstruction() {
        val inputInstruction = OutputInstruction(intArrayOf(4, 1), SUM)
        val codeList = intArrayOf(1, 2, 3)
        inputInstruction.run(codeList,0)
    }
}