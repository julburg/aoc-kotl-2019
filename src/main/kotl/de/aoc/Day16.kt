package de.aoc

import kotlin.math.ceil


fun day16a(signalInput: String): Int {
    var signals = signalInput.split("").filter { it.isNotEmpty() }.map { it.toInt() }
    for (phase in 1..100) {
        val newSignals = ArrayList<Int>()
        for (repetitions in 1..signals.size) {
            val pattern = getPattern(repetitions, signals.size)
            val signal = calcSignal(signals, pattern)
            newSignals.add(signal)
        }
        signals = newSignals

    }

    return signals.subList(0, 8).joinToString("").toInt()
}

private fun calcSignal(signals: List<Int>, pattern: ArrayList<Int>): Int {
    val signal = ArrayList<Int>()
    for (sig in 0 until signals.size) {
        val newSignal = signals.get(sig) * pattern.get(sig)
        signal.add(newSignal)
    }
    return cut(signal.sum())
}

fun cut(number: Int): Int {
    return number.toString().split("").filter { it.isNotEmpty() }.last().toInt()
}


fun getPattern(numberOfRepetitions: Int, patternLength: Int): ArrayList<Int> {
    val modifiedPattern = repeat(0, numberOfRepetitions)
    modifiedPattern.addAll(repeat(1, numberOfRepetitions))
    modifiedPattern.addAll(repeat(0, numberOfRepetitions))
    modifiedPattern.addAll(repeat(-1, numberOfRepetitions))

    val numberOfPatterns = ceil((patternLength + 1) / modifiedPattern.size.toDouble()).toInt()
    val pattern = ArrayList<Int>()
    for (num in 0 until numberOfPatterns) {
        pattern.addAll(modifiedPattern)
    }
    pattern.removeAt(0)
    return pattern
}

fun repeat(value: Int, numberofRepetitions: Int): ArrayList<Int> {
    val result = arrayListOf<Int>()
    for (num in 1..numberofRepetitions) {
        result.add(value)
    }
    return result
}
