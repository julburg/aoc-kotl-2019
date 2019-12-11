package de.aoc.day11

import de.aoc.day11.IntCodeProgram.*


/**
 * @author  Julia Burgard - burgard@synyx.de
 */

interface Instruction {

    fun run(codeList: LongArray, instructionPointer: Int): Int

}

class CalculateInstruction(input: LongArray, val opCode: OpCode, val calculate: (LongArray, ParameterI, ParameterI) -> Long, relativeBase: RelativeBase) : Instruction {
    var firstParameter: ParameterI = getParameter(getModeFirstValue(input.get(0).toInt()), input.get(1), relativeBase)
    var secondParameter: ParameterI = getParameter(getModeSecondValue(input.get(0).toInt()), input.get(2), relativeBase)
    var replaceParameter: ParameterI = getParameter(getModeThirdValue(input.get(0).toInt()), input.get(3), relativeBase)

    override fun run(codeList: LongArray, instructionPointer: Int): Int {
        codeList.set(replaceParameter.getPosition(), calculate(codeList, firstParameter, secondParameter))
        return instructionPointer + opCode.instructionDigits
    }
}

class JumpInstruction(input: LongArray, val opCode: OpCode, val shouldJump: (LongArray, ParameterI) -> Boolean, relativeBase: RelativeBase) : Instruction {
    var firstParameter: ParameterI = getParameter(getModeFirstValue(input.get(0).toInt()), input.get(1), relativeBase)
    var secondParameter: ParameterI = getParameter(getModeSecondValue(input.get(0).toInt()), input.get(2), relativeBase)

    override fun run(codeList: LongArray, instructionPointer: Int): Int {
        if (shouldJump(codeList, firstParameter)) {
            return secondParameter.getValue(codeList).toInt()
        }
        return instructionPointer + opCode.instructionDigits
    }
}


class CompareInstruction(var input: LongArray, val opCode: OpCode, val compare: (LongArray, ParameterI, ParameterI) -> Boolean, val relativeBase: RelativeBase) : Instruction {
    var firstParameter: ParameterI = getParameter(getModeFirstValue(input.get(0).toInt()), input.get(1), relativeBase)
    var secondParameter: ParameterI = getParameter(getModeSecondValue(input.get(0).toInt()), input.get(2), relativeBase)
    var replaceParameter: ParameterI = getParameter(getModeThirdValue(input.get(0).toInt()), input.get(3), relativeBase)

    override fun run(codeList: LongArray, instructionPointer: Int): Int {
        if (compare(codeList, firstParameter, secondParameter)) {
            codeList.set(replaceParameter.getPosition(), 1)
        } else {
            codeList.set(replaceParameter.getPosition(), 0)
        }
        return instructionPointer + opCode.instructionDigits
    }
}

class OutputInstructionInstruction(val input: LongArray, val opCode: OpCode, val outputValue: OutputValue, relativeBase: RelativeBase) : Instruction {
    var outputParameter: ParameterI = getParameter(getModeFirstValue(input.get(0).toInt()), input.get(1), relativeBase)

    override fun run(codeList: LongArray, instructionPointer: Int): Int {
        outputValue.setOutputValue(outputParameter.getValue(codeList))
        return instructionPointer + opCode.instructionDigits
    }
}

class InputInstructionInstruction(var input: LongArray, val opCode: OpCode, val inputValues: InputValues, var relativeBase: RelativeBase) : Instruction {
    var inputParameter: ParameterI = getParameter(getModeFirstValue(input.get(0).toInt()), input.get(1), relativeBase)

    override fun run(codeList: LongArray, instructionPointer: Int): Int {
        codeList.set(inputParameter.getPosition(), inputValues.getNextValue())
        return instructionPointer + opCode.instructionDigits
    }
}

class RelativeBaseOffsetInstruction(var input: LongArray, val opCode: OpCode, val inputValues: InputValues, val relativeBase: RelativeBase) : Instruction {
    var inputParameter: ParameterI = getParameter(getModeFirstValue(input.get(0).toInt()), input.get(1), relativeBase)

    override fun run(codeList: LongArray, instructionPointer: Int): Int {
        val value = inputParameter.getValue(codeList)
        relativeBase.increaseBy(value)
        return instructionPointer + opCode.instructionDigits
    }
}

class TerminateInstruction() : Instruction {

    override fun run(codeList: LongArray, instructionPointer: Int): Int {
        throw ProgramTerminatedException("Programm terminated")

    }
}

fun getParameter(mode: Int, value: Long, relativeBase: RelativeBase): ParameterI {

    return when (mode) {
        0 -> ParameterPositionMode(value)
        1 -> ParameterImmediateMode(value)
        2 -> ParameterRelativeMode(value, relativeBase)
        else -> {
            throw Exception("Parameter mode not known")
        }
    }

}

interface ParameterI {

    fun getValue(codeList: LongArray): Long

    fun getPosition(): Int

}

class ParameterImmediateMode(private var value: Long) : ParameterI {
    override fun getValue(codeList: LongArray): Long {
        return value;
    }

    override fun getPosition(): Int {
        throw UnsupportedOperationException("No Position supported in immediate mode");
    }

}

class ParameterPositionMode(private var value: Long) : ParameterI {

    override fun getValue(codeList: LongArray): Long {
        return codeList.get(getPosition());
    }

    override fun getPosition(): Int {
        return value.toInt()
    }

}

class ParameterRelativeMode(private var value: Long, var relativeBase: RelativeBase) : ParameterI {

    override fun getValue(codeList: LongArray): Long {
        return codeList.get(getPosition())
    }

    override fun getPosition(): Int {
        return relativeBase.getPosition(value.toInt())
    }

}

fun getModeFirstValue(value: Int): Int {
    return (Math.abs(value) / 100) % 10;
}

fun getModeSecondValue(value: Int): Int {
    return ((Math.abs(value) / 100) % 100) / 10
}
fun getModeThirdValue(value: Int): Int {
    return ((Math.abs(value) / 100) % 1000) / 100
}

fun getInstruction(opCode: OpCode, input: LongArray, inputValues: InputValues, outputValue: OutputValue, relativeBase: RelativeBase): Instruction {
    return when (opCode) {
        OpCode.SUM -> CalculateInstruction(input, opCode, sum(), relativeBase)
        OpCode.MULTIPLY -> CalculateInstruction(input, opCode, multiply(), relativeBase)
        OpCode.JUMPIFTRUE -> JumpInstruction(input, opCode, shouldJumpIfNotZero(), relativeBase)
        OpCode.JUMPIFFALSE -> JumpInstruction(input, opCode, shouldJumpIfZero(), relativeBase)
        OpCode.LESSTHAN -> CompareInstruction(input, opCode, isLessThan(), relativeBase)
        OpCode.EQUALS -> CompareInstruction(input, opCode, isEqualTo(), relativeBase)
        OpCode.OUTPUT -> OutputInstructionInstruction(input, opCode, outputValue, relativeBase)
        OpCode.INPUT -> InputInstructionInstruction(input, opCode, inputValues, relativeBase)
        OpCode.RELATIVEBASEOFFSET -> RelativeBaseOffsetInstruction(input, opCode, inputValues, relativeBase)
        OpCode.TERMINATE -> TerminateInstruction()
    }
}

class InputValues(initialInputValues: ArrayList<Long>) {
    private val inputValues = initialInputValues

    fun getNextValue(): Long {
        if (inputValues.isEmpty()) {
            throw WaitingOnInputException("Input Values empty");
        }
        val value = inputValues.get(0)
        inputValues.removeAt(0)
        return value
    }

}

class OutputValue() {
    var values = ArrayList<Long>()

    fun setOutputValue(outputValue: Long) {
        values.add(outputValue)
    }
}

private fun shouldJumpIfZero() = { codeList: LongArray, ParameterI: ParameterI -> ParameterI.getValue(codeList) == 0L }
private fun shouldJumpIfNotZero() = { codeList: LongArray, ParameterI: ParameterI -> ParameterI.getValue(codeList) != 0L }
private fun isEqualTo() = { codeList: LongArray, firstParameterI: ParameterI, secondParameterI: ParameterI -> firstParameterI.getValue(codeList) == secondParameterI.getValue(codeList) }
private fun isLessThan() = { codeList: LongArray, firstParameterI: ParameterI, secondParameterI: ParameterI -> firstParameterI.getValue(codeList) < secondParameterI.getValue(codeList) }
private fun multiply() = { codeList: LongArray, firstParameterI: ParameterI, secondParameterI: ParameterI -> firstParameterI.getValue(codeList) * secondParameterI.getValue(codeList) }
private fun sum() = { codeList: LongArray, firstParameterI: ParameterI, secondParameterI: ParameterI -> firstParameterI.getValue(codeList) + secondParameterI.getValue(codeList) }
