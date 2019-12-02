package de.aoc


fun day2b(input: String, expectedFirstValue: Int): Pair<Int, Int> {
    val codeList = input.split(",").map(String::toInt).toIntArray()

    for (noun in 0 until 99) {
        for (verb in 0 until 99) {
            val clone = codeList.clone()
            clone.set(1, noun)
            clone.set(2, verb)
            val restoredProgramm = restoreProgramAlarm(clone);
            if (restoredProgramm.get(0).equals(expectedFirstValue)) {
                return Pair(noun, verb)
            }

        }
    }

    throw Exception("Programm terminated without result")
}


fun day2a(input: String, noun: Int, verb: Int): IntArray {
    val codeList = input.split(",").map(String::toInt).toIntArray()
    codeList.set(1, noun)
    codeList.set(2, verb)

    return restoreProgramAlarm(codeList)
}

fun day2a(input: String): IntArray {
    val codeList = input.split(",").map(String::toInt).toIntArray()

    return restoreProgramAlarm(codeList)
}

private fun restoreProgramAlarm(codeList: IntArray): IntArray {
    var instructionPointer = 0;
    while (instructionPointer < codeList.size) {
        try {
            val intCodeProgramm = Instruction(instructionPointer, instructionPointer + 1, instructionPointer + 2, instructionPointer + 3);
            val value = codeList.calculateValue(intCodeProgramm);
            val replaceValueIndex = codeList.get(intCodeProgramm.outputIndexPosition)
            codeList.set(replaceValueIndex, value)
            instructionPointer += 4
        } catch (e: Exception) {
            break;
        }
    }

    return codeList
}


fun IntArray.calculateValue(instruction: Instruction): Int {
    val opCode = this.get(instruction.opCode);
    val firstValue = this.elementForIndexAtPosition(instruction.inputIndexPosition1)
    val secondValue = this.elementForIndexAtPosition(instruction.inputIndexPosition2)
    return opCode.executeOpCodeWith(firstValue, secondValue)
}

fun IntArray.elementForIndexAtPosition(index: Int): Int {
    return this.get(this.get(index))
}

fun Int.executeOpCodeWith(firstValue: Int, secondValue: Int): Int {
    when (this) {
        1 -> return firstValue + secondValue
        2 -> return firstValue * secondValue
        else -> {
            throw Exception("Programm terminated opcode: " + this)
        }
    }
}

data class Instruction(val opCode: Int, val inputIndexPosition1: Int, val inputIndexPosition2: Int, val outputIndexPosition: Int)
