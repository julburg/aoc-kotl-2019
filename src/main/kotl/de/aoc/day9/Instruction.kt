package de.aoc.day9

import de.aoc.day9.IntCodeProgram.*


/**
 * @author  Julia Burgard - burgard@synyx.de
 */

interface Instruction {

    fun run(codeList: IntArray, instructionPointer: Int): Int

}

class CalculateInstruction(input: IntArray, val opCode: OpCode, val calculate: (IntArray, ParameterI, ParameterI) -> Int, val valueX: ValueX) : Instruction {
    var firstParameter: ParameterI = getParameter(getModeFirstValue(input.get(0)), input.get(1), valueX)
    var secondParameter: ParameterI = getParameter(getModeSecondValue(input.get(0)), input.get(2), valueX)
    var replacePosition: Int = input.get(3)

    override fun run(codeList: IntArray, instructionPointer: Int): Int {
        codeList.set(replacePosition, calculate(codeList, firstParameter, secondParameter))
        return instructionPointer + opCode.instructionDigits
    }
}

class JumpInstruction(input: IntArray, val opCode: OpCode, val shouldJump: (IntArray, ParameterI) -> Boolean, val valueX: ValueX) : Instruction {
    var firstParameter: ParameterI = getParameter(getModeFirstValue(input.get(0)), input.get(1), valueX)
    var secondParameter: ParameterI = getParameter(getModeSecondValue(input.get(0)), input.get(2), valueX)

    override fun run(codeList: IntArray, instructionPointer: Int): Int {
        if (shouldJump(codeList, firstParameter)) {
            return secondParameter.getValue(codeList)
        }
        return instructionPointer + opCode.instructionDigits
    }
}


class CompareInstruction(input: IntArray, val opCode: OpCode, val compare: (IntArray, ParameterI, ParameterI) -> Boolean, val valueX: ValueX) : Instruction {
    var firstParameter: ParameterI = getParameter(getModeFirstValue(input.get(0)), input.get(1), valueX)
    var secondParameter: ParameterI = getParameter(getModeSecondValue(input.get(0)), input.get(2), valueX)
    var replacePosition: Int = input.get(3)

    override fun run(codeList: IntArray, instructionPointer: Int): Int {
        if (compare(codeList, firstParameter, secondParameter)) {
            codeList.set(replacePosition, 1)
        } else {
            codeList.set(replacePosition, 0)
        }
        return instructionPointer + opCode.instructionDigits
    }
}

class OutputInstructionInstruction(input: IntArray, val opCode: OpCode, val outputValue: OutputValue) : Instruction {
    var outputPosition = input.get(1)

    override fun run(codeList: IntArray, instructionPointer: Int): Int {
        outputValue.setOutputValue(codeList.get(outputPosition))
        return instructionPointer + opCode.instructionDigits
    }
}

class InputInstructionInstruction(input: IntArray, val opCode: OpCode, val inputValues: InputValues) : Instruction {
    var inputPosition = input.get(1)

    override fun run(codeList: IntArray, instructionPointer: Int): Int {
        codeList.set(inputPosition, inputValues.getNextValue())
        return instructionPointer + opCode.instructionDigits
    }
}

class OperationXInstruction(input: IntArray, val opCode: OpCode, val inputValues: InputValues, val valueX: ValueX) : Instruction {
    var increaseValue = input.get(1)

    override fun run(codeList: IntArray, instructionPointer: Int): Int {
        valueX.increaseBy(increaseValue)
        return instructionPointer + opCode.instructionDigits
    }
}

class TerminateInstruction() : Instruction {

    override fun run(codeList: IntArray, instructionPointer: Int): Int {
        throw ProgramTerminatedException("Programm terminated")

    }
}

fun getParameter(mode: Int, value: Int, valueX: ValueX): ParameterI {

    when (mode) {
        0 -> return ParameterPosition(value)
        1 -> return ParameterImmediate(value)
        2 -> return ParameterX(value, valueX)
        else -> {
            throw Exception("Parameter mode not known")
        }
    }

}

interface ParameterI {

    fun getValue(codeList: IntArray): Int

}

class ParameterImmediate(private var value: Int) : ParameterI {

    override fun getValue(codeList: IntArray): Int {
        return value;
    }
}

class ParameterPosition(private var value: Int) : ParameterI {

    override fun getValue(codeList: IntArray): Int {
        return codeList.get(value);
    }
}

class ParameterX(private var value: Int, var valueX: ValueX) : ParameterI {

    override fun getValue(codeList: IntArray): Int {
        return codeList.get(value + valueX.valueX);
    }
}

fun getModeFirstValue(value: Int): Int {
    return (Math.abs(value) / 100) % 10;
}

fun getModeSecondValue(value: Int): Int {
    return ((Math.abs(value) / 100) % 100) / 10
}

fun getInstruction(opCode: OpCode, input: IntArray, inputValues: InputValues, outputValue: OutputValue, valueX: ValueX): Instruction {
    return when (opCode) {
        OpCode.SUM -> CalculateInstruction(input, opCode, sum(), valueX)
        OpCode.MULTIPLY -> CalculateInstruction(input, opCode, multiply(), valueX)
        OpCode.JUMPIFTRUE -> JumpInstruction(input, opCode, shouldJumpIfNotZero(), valueX)
        OpCode.JUMPIFFALSE -> JumpInstruction(input, opCode, shouldJumpIfZero(), valueX)
        OpCode.LESSTHAN -> CompareInstruction(input, opCode, isLessThan(), valueX)
        OpCode.EQUALS -> CompareInstruction(input, opCode, isEqualTo(), valueX)
        OpCode.OUTPUT -> OutputInstructionInstruction(input, opCode, outputValue)
        OpCode.INPUT -> InputInstructionInstruction(input, opCode, inputValues)
        OpCode.OPERATIONX -> OperationXInstruction(input, opCode, inputValues, valueX)
        OpCode.TERMINATE -> TerminateInstruction()
    }
}

class InputValues(initialInputValues: ArrayList<Int>) {
    private val inputValues = initialInputValues

    fun getNextValue(): Int {
        if (inputValues.isEmpty()) {
            throw WaitingOnInputException("Input Values empty");
        }
        val value = inputValues.get(0)
        inputValues.removeAt(0)
        return value
    }

    fun addInputValues(values: ArrayList<Int>) {
        inputValues.addAll(values)
    }

}

class OutputValue() {
    var values = ArrayList<Int>()

    fun setOutputValue(outputValue: Int) {
        values.add(outputValue)
    }
}

private fun shouldJumpIfZero() = { codeList: IntArray, ParameterI: ParameterI -> ParameterI.getValue(codeList) == 0 }
private fun shouldJumpIfNotZero() = { codeList: IntArray, ParameterI: ParameterI -> ParameterI.getValue(codeList) != 0 }
private fun isEqualTo() = { codeList: IntArray, firstParameterI: ParameterI, secondParameterI: ParameterI -> firstParameterI.getValue(codeList) == secondParameterI.getValue(codeList) }
private fun isLessThan() = { codeList: IntArray, firstParameterI: ParameterI, secondParameterI: ParameterI -> firstParameterI.getValue(codeList) < secondParameterI.getValue(codeList) }
private fun multiply() = { codeList: IntArray, firstParameterI: ParameterI, secondParameterI: ParameterI -> firstParameterI.getValue(codeList) * secondParameterI.getValue(codeList) }
private fun sum() = { codeList: IntArray, firstParameterI: ParameterI, secondParameterI: ParameterI -> firstParameterI.getValue(codeList) + secondParameterI.getValue(codeList) }
