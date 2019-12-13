package de.aoc

import de.aoc.day13.IntCodeProgram
import de.aoc.day13.OutputValue
import java.awt.Point


fun day13a(codeList: LongArray): Long {

    val blockTileValue = 2L
    val intCodeProgram = IntCodeProgram(codeList.copyOf())
    var blockTileCount = 0L;
    var outputValue = OutputValue()

    while (!intCodeProgram.isTerminated) {
        outputValue = intCodeProgram.run()
    }

    var threeOutputs = arrayListOf<Long>()
    var tileMap = HashMap<Point, Int>()
    var currentPoint=Point(0,0)
    for (output in outputValue.values) {
        threeOutputs.add(output)
        if (threeOutputs.size % 3 == 0) {
            val distanceFromLeft = threeOutputs.get(0);
            val distanceFromTop = threeOutputs.get(1);
            val tileValue = threeOutputs.get(2);
            threeOutputs = arrayListOf<Long>()
            if (tileValue == blockTileValue) {
                blockTileCount = blockTileCount+1
            }
        }
    }

    return blockTileCount
}


