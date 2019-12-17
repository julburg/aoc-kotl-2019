package de.aoc

import java.lang.Math.ceil

const val oreIdentifier: String = "ORE"


fun day14b(reactionsInput: String): Long {
    var fuel = 2269320L
    var result = 1000000000000L;
    var resultBevore = 0L
    while (result > 0) {
        resultBevore = result
        val nextInput = reactionsInput.replace("1 FUEL", fuel.toString() + " FUEL")
        val ore = day14two(nextInput);
        result = 1000000000000L - ore;
        fuel += 1
    }
    return fuel - 2
}

fun day14two(reactionsInput: String): Long {
    val reactions = parseReactions(reactionsInput)
    val usage = HashMap<String, Long>()

    val outputChemicals = reactions.map { it.outputChemical.name }

    while (!usage.keys.containsAll(outputChemicals)) {
        val withoutUnsolvedUsage = reactions.filter {
            reactionsWithInputOf(it.outputChemical, reactions)
                    .none { !usage.containsKey(it) }
        }.filter { !usage.containsKey(it.outputChemical.name) }

        for (reactionToSolve in withoutUnsolvedUsage) {
            val chemicalToSolve = reactionToSolve.outputChemical
            val reactionsWithUsageOfReaction = reactions.filter { it.inputChemicals.map { it.name }.contains(chemicalToSolve.name) }
            if (reactionsWithUsageOfReaction.size == 0) {
                usage.put(chemicalToSolve.name, chemicalToSolve.amount)
            } else {
                val sum = reactionsWithUsageOfReaction.map { it.inputChemicals.filter { it.name.equals(chemicalToSolve.name) }.first().amount * usage.get(it.outputChemical.name)!! }.sum()
                val result = ceil(sum.toDouble() / chemicalToSolve.amount.toDouble()).toLong()
                usage.put(chemicalToSolve.name, result)
            }
        }
    }

    val oreReactions = reactions.filter { it.inputChemicals.map { it.name }.contains(oreIdentifier) }
    return oreReactions.map { usage.get(it.outputChemical.name)!! * it.inputChemicals.first().amount }.sum()

}


private fun reactionsWithInputOf(outputChemical: Chemical, reactions: List<Reaction>): List<String> {
    val filter = reactions.filter { it.inputChemicals.map { it.name }.contains(outputChemical.name) }
    return filter.map { it.outputChemical.name }
}


fun amountOre(amount: Long, reaction: Reaction): Long {

    return ceil(numberOfReactions(amount.toDouble(), reaction)).toLong() * reaction.inputChemicals.get(0).amount
}

private fun numberOfReactions(amount: Double, reaction: Reaction) =
        amount / reaction.outputChemical.amount.toDouble()


data class Chemical(val amount: Long, val name: String)
data class Reaction(val inputChemicals: List<Chemical>, val outputChemical: Chemical)

fun parseReactions(inputReactions: String): ArrayList<Reaction> {

    val reactionsSplitted = inputReactions.split("\n")
    val regex = "(.*) => (.*)".toRegex()
    val reactions = ArrayList<Reaction>()
    for (reactionSplitted in reactionsSplitted) {
        val (input, output) = regex.matchEntire(reactionSplitted)!!.destructured

        val outputChemicals = parseChemical(output)
        val inputChemicals = input.split(",").map { parseChemical(it.trim()) }
        reactions.add(Reaction(inputChemicals, outputChemicals))
    }
    return reactions
}

private fun parseChemical(chemical: String): Chemical {
    val regexChemical = "(\\d*) (\\w*)".toRegex()
    val (amount, name) = regexChemical.matchEntire(chemical)!!.destructured
    return Chemical(amount.toLong(), name)
}