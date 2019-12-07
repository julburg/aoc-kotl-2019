package de.aoc.day7

import de.aoc.day7.IntCodeProgram.OpCode

/**
 * @author  Julia Burgard - burgard@synyx.de
 */

interface Instruction {

    fun run(codeList: IntArray, instructionPointer: Int): Int

}

class CalculateInstruction(input: IntArray, val opCode: IntCodeProgram.OpCode, val calculate: (IntArray, Parameter, Parameter) -> Int) : Instruction {
    var firstParameter: Parameter = Parameter(getModeFirstValue(input.get(0)), input.get(1))
    var secondParameter: Parameter = Parameter(getModeSecondValue(input.get(0)), input.get(2))
    var replacePosition: Int = input.get(3)

    override fun run(codeList: IntArray, instructionPointer: Int): Int {
        codeList.set(replacePosition, calculate(codeList, firstParameter, secondParameter))
        return instructionPointer + opCode.instructionDigits
    }
}

class JumpInstruction(input: IntArray, val opCode: OpCode, val shouldJump: (IntArray, Parameter) -> Boolean) : Instruction {
    var firstParameter: Parameter = Parameter(getModeFirstValue(input.get(0)), input.get(1))
    var secondParameter: Parameter = Parameter(getModeSecondValue(input.get(0)), input.get(2))

    override fun run(codeList: IntArray, instructionPointer: Int): Int {
        if (shouldJump(codeList, firstParameter)) {
            return secondParameter.getValue(codeList)
        }
        return instructionPointer + opCode.instructionDigits
    }
}


class CompareInstruction(input: IntArray, val opCode: OpCode, val compare: (IntArray, Parameter, Parameter) -> Boolean) : Instruction {
    var firstParameter: Parameter = Parameter(getModeFirstValue(input.get(0)), input.get(1))
    var secondParameter: Parameter = Parameter(getModeSecondValue(input.get(0)), input.get(2))
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

class TerminateInstruction() : Instruction {

    override fun run(codeList: IntArray, instructionPointer: Int): Int {
        throw UnsupportedOperationException("Programm terminated" + this)

    }
}

class Parameter(private var mode: Int, private var value: Int) {

    fun getValue(codeList: IntArray): Int {
        if (mode == 0) {
            return codeList.get(value)
        } else {
            return value;
        }
    }
}

fun getModeFirstValue(value: Int): Int {
    return (Math.abs(value) / 100) % 10;
}

fun getModeSecondValue(value: Int): Int {
    return ((Math.abs(value) / 100) % 100) / 10
}

fun getInstruction(opCode: OpCode, input: IntArray, inputValues: InputValues, outputValue: OutputValue): Instruction {
    return when (opCode) {
        OpCode.SUM -> CalculateInstruction(input, opCode, sum())
        OpCode.MULTIPLY -> CalculateInstruction(input, opCode, multiply())
        OpCode.JUMPIFTRUE -> JumpInstruction(input, opCode, shouldJumpIfNotZero())
        OpCode.JUMPIFFALSE -> JumpInstruction(input, opCode, shouldJumpIfZero())
        OpCode.LESSTHAN -> CompareInstruction(input, opCode, isLessThan())
        OpCode.EQUALS -> CompareInstruction(input, opCode, isEqualTo())
        OpCode.OUTPUT -> OutputInstructionInstruction(input, opCode, outputValue)
        OpCode.INPUT -> InputInstructionInstruction(input, opCode, inputValues)
        OpCode.TERMINATE -> TerminateInstruction()
    }
}

class InputValues(val firstInputValue: Int, private val lastOutputValue: OutputValue) {
    private var firstInputValueUsed = false;

    fun getNextValue(): Int {
        if (firstInputValueUsed == true) {
            return lastOutputValue.value
        }
        firstInputValueUsed=true
        return firstInputValue
    }

}

class OutputValue(initValue : Int) {
    var value=initValue

    fun setOutputValue(outputValue: Int) {
        value = outputValue
    }
}

private fun shouldJumpIfZero() = { codeList: IntArray, parameter: Parameter -> parameter.getValue(codeList) == 0 }
private fun shouldJumpIfNotZero() = { codeList: IntArray, parameter: Parameter -> parameter.getValue(codeList) != 0 }
private fun isEqualTo() = { codeList: IntArray, firstParameter: Parameter, secondParameter: Parameter -> firstParameter.getValue(codeList) == secondParameter.getValue(codeList) }
private fun isLessThan() = { codeList: IntArray, firstParameter: Parameter, secondParameter: Parameter -> firstParameter.getValue(codeList) < secondParameter.getValue(codeList) }
private fun multiply() = { codeList: IntArray, firstParameter: Parameter, secondParameter: Parameter -> firstParameter.getValue(codeList) * secondParameter.getValue(codeList) }
private fun sum() = { codeList: IntArray, firstParameter: Parameter, secondParameter: Parameter -> firstParameter.getValue(codeList) + secondParameter.getValue(codeList) }
