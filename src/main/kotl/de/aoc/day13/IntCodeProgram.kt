package de.aoc.day13

import de.aoc.day13.IntCodeProgram.OpCode.Companion.getOpCodeByIdentifier


/**
 * @author  Julia Burgard - burgard@synyx.de
 */
class IntCodeProgram(private var codeList: LongArray) {

    var instructionPointer = 0
    var isTerminated = false
    val outputValue = OutputValue()
    var relativeBase = RelativeBase(0)

    fun run(): OutputValue {
        val inputValues = InputValues(arrayListOf())
        while (instructionPointer < codeList.size) {
            try {
                val opCode = getOpCodeByIdentifier(codeList.get(instructionPointer) % 100);
                val instructionList = codeList.instructionList(instructionPointer, opCode)
                val instruction = getInstruction(opCode, instructionList, inputValues, outputValue, relativeBase);
                instructionPointer = instruction.run(codeList, instructionPointer)
            } catch (e: WaitingOnInputException) {
                break
            } catch (e: ProgramTerminatedException) {
                println("Program terminated")
                isTerminated = true
                break;
            }
        }
        return outputValue
    }


    fun LongArray.instructionList(startPoint: Int, opCode: OpCode): LongArray {
        return this.toList().subList(startPoint, startPoint + opCode.instructionDigits).toLongArray()
    }


    data class RelativeBase(var relativeBase: Long) {
        fun increaseBy(value: Long) {
            relativeBase = relativeBase + value
        }

        fun getPosition(value: Int): Int {
            return value + relativeBase.toInt()
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
        RELATIVEBASEOFFSET(9, 2),
        TERMINATE(99, 0);

        companion object {
            fun getOpCodeByIdentifier(id: Long): OpCode = OpCode.values().first { it.id == id.toInt() }
        }
    }

    class ProgramTerminatedException(message: String) : Exception(message)
    class WaitingOnInputException(message: String) : Exception(message)


}