package de.aoc.day7

import com.marcinmoskala.math.permutations

/**
 * @author  Julia Burgard - burgard@synyx.de
 */
class AmplificationCircuit(private var codeList: IntArray) {

    fun run(): Int {
        return setOf(0, 1, 2, 3, 4).permutations().map { runForPhase(it, 0) }.max()!!

    }

    fun runForPhase(phaseSetting: List<Int>, input: Int): Int {
        var output = input
        for (phase in phaseSetting) {
            output = IntCodeProgram(codeList.copyOf(), arrayListOf(phase, output)).run().value
        }
        return output
    }

    fun runWithFeedbackLoop(): Int {
        val permutations = setOf(5, 6, 7, 8, 9).permutations()

        val outputs = ArrayList<Int>()
        var output = 0
        for (permutation in permutations) {
            output = runForPhase(permutation, output)
            outputs.add(output)
        }

        return outputs.max()!!
    }

    fun runForPermutation(permutation: List<Int>, outputs: ArrayList<Int>): Int {
        var output = 0
        var firstRun = true
        while (output != 139629729) {
            output = runForPhase(permutation, output)
            outputs.add(output)
            firstRun = false
        }
        return output
    }


}
