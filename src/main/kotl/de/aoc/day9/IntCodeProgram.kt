package de.aoc.day9

import de.aoc.day9.IntCodeProgram.OpCode.Companion.getOpCodeByIdentifier


/**
 * @author  Julia Burgard - burgard@synyx.de
 */
class IntCodeProgram(private var codeList: IntArray, initialInputValues: ArrayList<Int>) {

    val inputValues = InputValues(initialInputValues)
    var instructionPointer = 0
    var isTerminated = false
    var lastOutputValue = 0
    var valueX = ValueX(0)

    fun run(): OutputValue {
        val outputValue = OutputValue()
        while (instructionPointer < codeList.size) {
            try {
                val opCode = getOpCodeByIdentifier(codeList.get(instructionPointer) % 100);
                val instructionList = codeList.instructionList(instructionPointer, opCode)
                val instruction = getInstruction(opCode, instructionList, inputValues, outputValue, valueX);
                instructionPointer = instruction.run(codeList, instructionPointer)

            } catch (e: WaitingOnInputException) {
                break
            } catch (e: ProgramTerminatedException) {
                println("something terminated")
                isTerminated = true
                break;
            }
        }
        lastOutputValue = outputValue.values.first()
        return outputValue
    }


    fun IntArray.instructionList(startPoint: Int, opCode: OpCode): IntArray {
        return this.toList().subList(startPoint, startPoint + opCode.instructionDigits).toIntArray()
    }


    data class ValueX(var valueX: Int) {
        fun increaseBy(value: Int) {
            valueX = valueX + value
        }
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
        OPERATIONX(9, 2),
        TERMINATE(99, 0);

        companion object {
            fun getOpCodeByIdentifier(id: Int): OpCode = OpCode.values().first { it.id == id }
        }
    }

    class ProgramTerminatedException(message: String) : Exception(message)
    class WaitingOnInputException(message: String) : Exception(message)


}