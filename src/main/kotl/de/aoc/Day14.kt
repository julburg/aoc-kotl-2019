package de.aoc

const val fuelIdentifier: String = "FUEL"
const val oreIdentifier: String = "ORE"

fun day14a(reactionsInput: String): Int {
    val reactions = parseReactions(reactionsInput)
    val chemicalsForOre = getChemicalsForOre(Chemical(1, fuelIdentifier), reactions, hashMapOf())
    return chemicalsForOre.map { amountOre(it.key, it.value, reactions) }.sum()
}

private fun amountOre(name: String, amount: Int, reactions: ArrayList<Reaction>): Int {
    val reactionOre = reactions.filter { it.outputChemical.name.equals(name) }.first()

    return amountOre(amount, reactionOre)
}

fun getChemicalsForOre(chemical: Chemical, reactions: List<Reaction>, result: HashMap<String, Int>): Map<String, Int> {
    val reaction = reactions.first { it.outputChemical.name.equals(chemical.name) }
    val numberOfReactions = numberOfReactions(chemical.amount, reaction)

    if (hasOreChemical(reaction)) {
        val value = chemical.amount
        if (result.contains(reaction.outputChemical.name)) {
            result.put(reaction.outputChemical.name, result.get(reaction.outputChemical.name)!! + value)
        } else {
            result.put(reaction.outputChemical.name, value)
        }
        return result
    }

    for (inputChemical in reaction.inputChemicals) {
        getChemicalsForOre(Chemical(inputChemical.amount * numberOfReactions, inputChemical.name), reactions, result)
    }

    return result
}

fun amountOre(amount: Int, reaction: Reaction): Int {

    return numberOfReactions(amount, reaction) * reaction.inputChemicals.get(0).amount
}

private fun numberOfReactions(amount: Int, reaction: Reaction) =
        Math.ceil(amount.toDouble() / reaction.outputChemical.amount.toDouble()).toInt()

private fun hasOreChemical(reaction: Reaction) =
        reaction.inputChemicals.filter { it.name.equals(oreIdentifier) }.isNotEmpty()


class Chemical(val amount: Int, val name: String)
class Reaction(val inputChemicals: List<Chemical>, val outputChemical: Chemical)

fun parseReactions(inputReactions: String): ArrayList<Reaction> {

    val reactionsSplitted = inputReactions.split("\n")
    val regex = "(.*) => (.*)".toRegex()
    val reactions = ArrayList<Reaction>();
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