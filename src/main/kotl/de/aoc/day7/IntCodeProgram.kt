package de.aoc.day7

import de.aoc.day7.IntCodeProgram.OpCode.Companion.getOpCodeByIdentifier


/**
 * @author  Julia Burgard - burgard@synyx.de
 */
class IntCodeProgram(private var codeList: IntArray,val initialInputValues: ArrayList<Int>) {

    fun run(): OutputValue {
        val outputValue = OutputValue(initialInputValues.get(1))
        val inputValues = InputValues(initialInputValues.get(0), outputValue)
        var instructionPointer = 0;
        while (instructionPointer < codeList.size) {
            try {
                val opCode = getOpCodeByIdentifier(codeList.get(instructionPointer) % 100);
                val instructionList = codeList.instructionList(instructionPointer, opCode)
                val instruction = getInstruction(opCode, instructionList, inputValues, outputValue);
                instructionPointer = instruction.run(codeList, instructionPointer)

            } catch (e: UnsupportedOperationException) {
                break;
            }
        }
        println("Output: "+ outputValue.value)
        return outputValue
    }

    fun IntArray.instructionList(startPoint: Int, opCode: OpCode): IntArray {
        return this.toList().subList(startPoint, startPoint + opCode.instructionDigits).toIntArray()
    }


    enum class OpCode(val id: Int, val instructionDigits: Int) {
        SUM(1, 4),
        MULTIPLY(2, 4),
        INPUT(3, 2),
        OUTPUT(4, 2),
        JUMPIFTRUE(5, 3),
        JUMPIFFALSE(6, 3),
        LESSTHAN(7, 4),
        EQUALS(8, 4),
        TERMINATE(99, 0);

        companion object {
            fun getOpCodeByIdentifier(id: Int): OpCode = OpCode.values().first { it.id == id }
        }
    }


}