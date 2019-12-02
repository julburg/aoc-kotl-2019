package de.aoc


fun day2a(input: String): IntArray {
    val codeList = input.split(",").map(String::toInt).toIntArray()
    codeList.set(1, 12)
    codeList.set(2, 2)

    var currentIndex = 0;
    while (currentIndex < codeList.size) {
        try {
            val intCodeProgramm = IntCodeProgramm(currentIndex, currentIndex + 1, currentIndex + 2, currentIndex + 3);
            val value = codeList.calculateValue(intCodeProgramm);
            val replaceValueIndex = codeList.get(intCodeProgramm.outputIndexPosition)
            codeList.set(replaceValueIndex, value)
            currentIndex += 4
        } catch (e: Exception) {
            break;
        }
    }

    return codeList
}

fun IntArray.calculateValue(intCodeProgramm: IntCodeProgramm): Int {
    val opCode = this.get(intCodeProgramm.opCode);
    val firstValue = this.elementForIndexAtPosition(intCodeProgramm.inputIndexPosition1)
    val secondValue = this.elementForIndexAtPosition(intCodeProgramm.inputIndexPosition2)
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

data class IntCodeProgramm(val opCode: Int, val inputIndexPosition1: Int, val inputIndexPosition2: Int, val outputIndexPosition: Int)
