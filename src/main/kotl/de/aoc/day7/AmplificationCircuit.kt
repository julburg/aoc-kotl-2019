package de.aoc.day7

import com.marcinmoskala.math.permutations

/**
 * @author  Julia Burgard - burgard@synyx.de
 */
class AmplificationCircuit(private var codeList: IntArray) {

    fun run(): Int {
        return setOf(5, 6, 7, 8, 9).permutations().map { runForPhase(it) }.max()!!
    }


    fun runForPhase(phaseSetting: List<Int>): Int {
        val ampA = IntCodeProgram("AmpA", codeList.copyOf(), arrayListOf(phaseSetting.get(0), 0))
        val ampB = IntCodeProgram("AmpB", codeList.copyOf(), arrayListOf(phaseSetting.get(1)))
        val ampC = IntCodeProgram("AmpC", codeList.copyOf(), arrayListOf(phaseSetting.get(2)))
        val ampD = IntCodeProgram("AmpD", codeList.copyOf(), arrayListOf(phaseSetting.get(3)))
        val ampE = IntCodeProgram("AmpE", codeList.copyOf(), arrayListOf(phaseSetting.get(4)))
        val amplifiers = arrayListOf(ampA, ampB, ampC, ampD, ampE)

        var output = arrayListOf<Int>()
        var currentAmplifier = 0

        while (amplifiers.filter { it.isTerminated == false }.isNotEmpty()) {
            output = amplifiers.get(currentAmplifier).run(output).values
            currentAmplifier = (currentAmplifier + 1) % 5
        }
        return ampE.lastOutputValue
    }
}



