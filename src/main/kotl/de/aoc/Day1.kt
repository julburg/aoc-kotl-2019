package de.aoc


fun day1a(input: String): Int {

    val massList = input.split("\n").map(String::toInt).toIntArray()
    return massList.map { mass -> calculateFuel(mass) }.sum();
}

fun day1b(input: String): Int {

    val massList = input.split("\n").map(String::toInt).toIntArray()
    return massList.map { mass -> calculateWithAdditionalFuel(mass) }.sum();
}

fun calculateWithAdditionalFuel(mass: Int): Int {
    var fuel = calculateFuel(mass)
    if (fuel > 0) {
        fuel = fuel + calculateWithAdditionalFuel(fuel)
    }
    return fuel
}

private fun calculateFuel(mass: Int) :Int{
    val fuel = mass / 3 - 2
    if(fuel<1){
        return 0
    }
    return fuel;
}

