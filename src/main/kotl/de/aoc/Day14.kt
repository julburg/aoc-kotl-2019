package de.aoc

import java.lang.Math.ceil

const val oreIdentifier: String = "ORE"


fun day14two(reactionsInput: String): Int {
    val reactions = parseReactions(reactionsInput)
    val usage = HashMap<String, Int>()

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
                val result = ceil(sum.toDouble() / chemicalToSolve.amount.toDouble()).toInt()
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


fun amountOre(amount: Int, reaction: Reaction): Int {

    return ceil(numberOfReactions(amount.toDouble(), reaction)).toInt() * reaction.inputChemicals.get(0).amount
}

private fun numberOfReactions(amount: Double, reaction: Reaction) =
        amount / reaction.outputChemical.amount.toDouble()


data class Chemical(val amount: Int, val name: String)
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
    return Chemical(amount.toInt(), name)
}