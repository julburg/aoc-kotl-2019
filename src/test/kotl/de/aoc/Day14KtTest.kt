package de.aoc

import org.junit.Test
import java.io.File
import kotlin.test.assertEquals

/**
 * @author  Julia Burgard - burgard@synyx.de
 */
class Day14KtTest {


    @Test
    fun day14a() {
        val reactionsInput = File("inputDay14").inputStream().bufferedReader().use { it.readText() }

        val amountOfOre = day14two(reactionsInput)
        assertEquals(598038, amountOfOre)
    }

    @Test
    fun day14b() {
        println(82892753-75120192)
        println(1000000000000L / 13312L)
        println(1000000000000000000L / 13312L)
        println(75120192307692*13312L+4096)

        var ore = 1000000000000L
        var res = ore.toDouble() % 13312L
        println(82892753L * 13312L)
        println(1103468327936L - ore)
        println(5586022L * 180697L)
        println(2210736L * 460664L)


    }

    @Test
    fun day14aExampleOne() {
        val reactionsInput = "10 ORE => 10 A\n" +
                "1 ORE => 1 B\n" +
                "7 A, 1 B => 1 C\n" +
                "7 A, 1 C => 1 D\n" +
                "7 A, 1 D => 1 E\n" +
                "7 A, 1 E => 1 FUEL"

        val amountOfOre = day14two(reactionsInput)
        assertEquals(31, amountOfOre)
    }

    @Test
    fun test() {
        val reactionsInput = "10 ORE => 10 A\n" +
                "4 A => 10 D\n" +
                "17 D => 1 F\n" +
                "2 D, 1 F => 1 E\n" +
                "1 D, 1 E => 1 FUEL"

        val amountOfOre = day14two(reactionsInput)
        assertEquals(10, amountOfOre)
    }


    @Test
    fun day14aExampleTwo() {
        val reactionsInput = "9 ORE => 2 A\n" +
                "8 ORE => 3 B\n" +
                "7 ORE => 5 C\n" +
                "3 A, 4 B => 1 AB\n" +
                "5 B, 7 C => 1 BC\n" +
                "4 C, 1 A => 1 CA\n" +
                "2 AB, 3 BC, 4 CA => 2 FUEL"

        val amountOfOre = day14two(reactionsInput)
        assertEquals(165, amountOfOre)
        //323 158
        //480 157
        //638 158
        //796 158
    }


    @Test
    fun day14aExampleThree() {
        val reactionsInput = "157 ORE => 5 NZVS\n" +
                "165 ORE => 6 DCFZ\n" +
                "44 XJWVT, 5 KHKGT, 1 QDVJ, 29 NZVS, 9 GPVTF, 48 HKGWZ => 1 FUEL\n" +
                "12 HKGWZ, 1 GPVTF, 8 PSHF => 9 QDVJ\n" +
                "179 ORE => 7 PSHF\n" +
                "177 ORE => 5 HKGWZ\n" +
                "7 DCFZ, 7 PSHF => 2 XJWVT\n" +
                "165 ORE => 2 GPVTF\n" +
                "3 DCFZ, 7 NZVS, 5 HKGWZ, 10 PSHF => 8 KHKGT"

        val amountOfOre = day14two(reactionsInput)
        //1205354336
        //1584077485
        println("huhu" + (25590 - 13312))
        println(37025 - 25590)
        assertEquals(13312, amountOfOre)
    }


    @Test
    fun day14aExampleFour() {
        val reactionsInput = "2 VPVL, 7 FWMGM, 2 CXFTF, 11 MNCFX => 1 STKFG\n" +
                "17 NVRVD, 3 JNWZP => 8 VPVL\n" +
                "53 STKFG, 6 MNCFX, 46 VJHF, 81 HVMC, 68 CXFTF, 25 GNMV => 1 FUEL\n" +
                "22 VJHF, 37 MNCFX => 5 FWMGM\n" +
                "139 ORE => 4 NVRVD\n" +
                "144 ORE => 7 JNWZP\n" +
                "5 MNCFX, 7 RFSQX, 2 FWMGM, 2 VPVL, 19 CXFTF => 3 HVMC\n" +
                "5 VJHF, 7 MNCFX, 9 VPVL, 37 CXFTF => 6 GNMV\n" +
                "145 ORE => 6 MNCFX\n" +
                "1 NVRVD => 8 CXFTF\n" +
                "1 VJHF, 6 MNCFX => 4 RFSQX\n" +
                "176 ORE => 6 VJHF"

        val amountOfOre = day14two(reactionsInput)
        assertEquals(180697, amountOfOre)
    }


    @Test
    fun day14aExampleFive() {
        val reactionsInput = "171 ORE => 8 CNZTR\n" +
                "7 ZLQW, 3 BMBT, 9 XCVML, 26 XMNCP, 1 WPTQ, 2 MZWV, 1 RJRHP => 4 PLWSL\n" +
                "114 ORE => 4 BHXH\n" +
                "14 VRPVC => 6 BMBT\n" +
                "6 BHXH, 18 KTJDG, 12 WPTQ, 7 PLWSL, 31 FHTLT, 37 ZDVW => 1 FUEL\n" +
                "6 WPTQ, 2 BMBT, 8 ZLQW, 18 KTJDG, 1 XMNCP, 6 MZWV, 1 RJRHP => 6 FHTLT\n" +
                "15 XDBXC, 2 LTCX, 1 VRPVC => 6 ZLQW\n" +
                "13 WPTQ, 10 LTCX, 3 RJRHP, 14 XMNCP, 2 MZWV, 1 ZLQW => 1 ZDVW\n" +
                "5 BMBT => 4 WPTQ\n" +
                "189 ORE => 9 KTJDG\n" +
                "1 MZWV, 17 XDBXC, 3 XCVML => 2 XMNCP\n" +
                "12 VRPVC, 27 CNZTR => 2 XDBXC\n" +
                "15 KTJDG, 12 BHXH => 5 XCVML\n" +
                "3 BHXH, 2 VRPVC => 7 MZWV\n" +
                "121 ORE => 7 VRPVC\n" +
                "7 XCVML => 6 RJRHP\n" +
                "5 BHXH, 4 VRPVC => 5 LTCX"

        val amountOfOre = day14two(reactionsInput)
        assertEquals(2210736, amountOfOre)
    }

    @Test
    fun oreAmountTest() {
        var oreAmount = amountOre(7, Reaction(arrayListOf(Chemical(10, "Ore")), Chemical(1, "A")))
        assertEquals(70, oreAmount)

        oreAmount = amountOre(7, Reaction(arrayListOf(Chemical(10, "Ore")), Chemical(2, "A")))
        assertEquals(40, oreAmount)

        oreAmount = amountOre(1, Reaction(arrayListOf(Chemical(10, "Ore")), Chemical(1, "A")))
        assertEquals(10, oreAmount)
    }


    @Test
    fun parseReactionsTest() {
        val reactionsInput = "10 ORE => 10 A\n" +
                "1 ORE => 1 B\n" +
                "7 A, 1 B => 1 C\n" +
                "7 A, 1 C => 1 D\n" +
                "7 A, 1 D => 1 E\n" +
                "7 A, 1 E => 1 FUEL"
        val reactions = parseReactions(reactionsInput);

        assertEquals(6, reactions.size)
        assertEquals("A", reactions.get(0).outputChemical.name)
        assertEquals(10, reactions.get(0).outputChemical.amount)
        assertEquals(10, reactions.get(0).inputChemicals.get(0).amount)
        assertEquals("ORE", reactions.get(0).inputChemicals.get(0).name)
    }

}


