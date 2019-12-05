package de.aoc.day5

/**
 * @author  Julia Burgard - burgard@synyx.de
 */

interface Instruction {

    fun run(codeList: IntArray, instructionPointer: Int): Int

}

class MultiplyAndSumInstruction(input: IntArray, val opCode: IntCodeProgram.OpCode) : Instruction {
    var firstParameter: Parameter = Parameter(getModeFirstValue(input.get(0)), input.get(1))
    var secondParameter: Parameter = Parameter(getModeSecondValue(input.get(0)), input.get(2))
    var replacePosition: Int = input.get(3)

    override fun run(codeList: IntArray, instructionPointer: Int): Int {
        codeList.set(replacePosition, executeOpCodeWith(firstParameter.getValue(codeList), secondParameter.getValue(codeList)))
        return instructionPointer + opCode.getInstructionDigits()

    }


    private fun executeOpCodeWith(firstValue: Int, secondValue: Int): Int {
        when (opCode) {
            IntCodeProgram.OpCode.SUM -> return firstValue + secondValue
            IntCodeProgram.OpCode.MULTIPLY -> return firstValue * secondValue
            else -> {
                throw Exception("Only with multiply and sum! Programm terminated opcode: " + this)
            }
        }
    }
}

class JumpIfTrue(input: IntArray, val opCode: IntCodeProgram.OpCode) : Instruction {
    var firstParameter: Parameter = Parameter(getModeFirstValue(input.get(0)), input.get(1))
    var secondParameter: Parameter = Parameter(getModeSecondValue(input.get(0)), input.get(2))

    override fun run(codeList: IntArray, instructionPointer: Int): Int {
        if (firstParameter.getValue(codeList) != 0) {
            return secondParameter.getValue(codeList)
        }
        return instructionPointer + opCode.getInstructionDigits()
    }
}

class JumpIfFalse(input: IntArray, val opCode: IntCodeProgram.OpCode) : Instruction {
    var firstParameter: Parameter = Parameter(getModeFirstValue(input.get(0)), input.get(1))
    var secondParameter: Parameter = Parameter(getModeSecondValue(input.get(0)), input.get(2))

    override fun run(codeList: IntArray, instructionPointer: Int): Int {
        if (firstParameter.getValue(codeList) == 0) {
            return secondParameter.getValue(codeList)
        }
        return instructionPointer + opCode.getInstructionDigits()
    }
}


class LessThan(input: IntArray, val opCode: IntCodeProgram.OpCode) : Instruction {
    var firstParameter: Parameter = Parameter(getModeFirstValue(input.get(0)), input.get(1))
    var secondParameter: Parameter = Parameter(getModeSecondValue(input.get(0)), input.get(2))
    var replacePosition: Int = input.get(3)

    override fun run(codeList: IntArray, instructionPointer: Int): Int {
        if (firstParameter.getValue(codeList) < secondParameter.getValue(codeList)) {
            codeList.set(replacePosition, 1)
        } else {
            codeList.set(replacePosition, 0)
        }
        return instructionPointer + opCode.getInstructionDigits()
    }
}

class Equal(input: IntArray, val opCode: IntCodeProgram.OpCode) : Instruction {
    var firstParameter: Parameter = Parameter(getModeFirstValue(input.get(0)), input.get(1))
    var secondParameter: Parameter = Parameter(getModeSecondValue(input.get(0)), input.get(2))
    var replacePosition: Int = input.get(3)

    override fun run(codeList: IntArray, instructionPointer: Int): Int {
        if (firstParameter.getValue(codeList) == secondParameter.getValue(codeList)) {
            codeList.set(replacePosition, 1)
        } else {
            codeList.set(replacePosition, 0)
        }
        return instructionPointer + opCode.getInstructionDigits()
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


class OutputInstruction(input: IntArray, val opCode: IntCodeProgram.OpCode) : Instruction {
    var outputPosition = input.get(1)

    override fun run(codeList: IntArray, instructionPointer: Int): Int {
        println("Output: " + codeList.get(outputPosition))
        return instructionPointer + opCode.getInstructionDigits()
    }

}


class InputInstruction(input: IntArray, val opCode: IntCodeProgram.OpCode) : Instruction {
    var inputPosition = input.get(1)

    override fun run(codeList: IntArray, instructionPointer: Int): Int {
        codeList.set(inputPosition, 5)
        return instructionPointer + opCode.getInstructionDigits()
    }

}

class TerminateInstruction() : Instruction {

    override fun run(codeList: IntArray, instructionPointer: Int): Int {
        throw UnsupportedOperationException("Programm terminated" + this)

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
