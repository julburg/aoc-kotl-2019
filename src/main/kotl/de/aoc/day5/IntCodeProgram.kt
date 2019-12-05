package de.aoc.day5

import de.aoc.day5.IntCodeProgram.OpCode.*

/**
 * @author  Julia Burgard - burgard@synyx.de
 */
class IntCodeProgram(private var codeList: IntArray) {

    fun run(): IntArray {
        var instructionPointer = 0;
        while (instructionPointer < codeList.size) {
            try {
                val opCode = getOpCode(codeList.get(instructionPointer) % 100);
                val instructionList = codeList.instructionList(instructionPointer, opCode)
                val instruction = getInstruction(opCode, instructionList);
                instructionPointer = instruction.run(codeList, instructionPointer)
            } catch (e: UnsupportedOperationException) {
                break;
            }
        }

        return codeList
    }

    fun IntArray.instructionList(startPoint: Int, opCode: OpCode): IntArray {
        return this.toList().subList(startPoint, startPoint + opCode.getInstructionDigits()).toIntArray()
    }


    fun getInstruction(opCode: OpCode, input: IntArray): Instruction {
        return when (opCode) {
            SUM -> MultiplyAndSumInstruction(input, opCode)
            MULTIPLY -> MultiplyAndSumInstruction(input, opCode)
            OUTPUT -> OutputInstruction(input, opCode)
            INPUT -> InputInstruction(input, opCode)
            JUMPIFFALSE->JumpIfFalse(input,opCode)
            JUMPIFTRUE->JumpIfTrue(input,opCode)
            LESSTHAN->LessThan(input,opCode)
            EQUALS->Equal(input,opCode)
            TERMINATE -> TerminateInstruction()
        }
    }

    enum class OpCode {
        JUMPIFTRUE {
            override fun getInstructionDigits(): Int {
                return 3
            }
        },
        JUMPIFFALSE {
            override fun getInstructionDigits(): Int {
                return 3
            }
        },
        LESSTHAN {
            override fun getInstructionDigits(): Int {
                return 4
            }
        },
        EQUALS {
            override fun getInstructionDigits(): Int {
                return 4
            }
        },
        MULTIPLY {
            override fun getInstructionDigits(): Int {
                return 4
            }
        },
        SUM {
            override fun getInstructionDigits(): Int {
                return 4
            }
        },
        OUTPUT {
            override fun getInstructionDigits(): Int {
                return 2
            }
        },
        INPUT {
            override fun getInstructionDigits(): Int {
                return 2
            }
        },
        TERMINATE {
            override fun getInstructionDigits(): Int {
                return 0
            }
        };

        abstract fun getInstructionDigits(): Int

    }

    fun getOpCode(value: Int): OpCode {
        return when (value) {
            1 -> SUM
            2 -> OpCode.MULTIPLY
            3 -> INPUT
            4 -> OUTPUT
            5 -> JUMPIFTRUE
            6 -> JUMPIFFALSE
            7 -> LESSTHAN
            8 -> EQUALS
            99 -> TERMINATE
            else -> {
                throw Exception("OpCode does not exist: " + value)
            }
        }
    }


}