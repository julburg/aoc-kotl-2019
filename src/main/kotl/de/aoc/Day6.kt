package de.aoc


fun day6a(map: String): Int {
    val orbitElements = map.split("\n").map {
        OrbitElement(it.split(")").get(0), it.split(")").get(1))
    };

    val planetsWithCount = HashMap<String, Int>()

    val planets = orbitElements.map { it.planet }.toSet();
    val planetsInOrbit = orbitElements.map { it.planetInOrbit }.toSet();
    val allPlanets = planets.plus(planetsInOrbit);
    for (planet in allPlanets) {
        planetsWithCount.put(planet, calculateIndirectOrbitCount(planet, orbitElements, 0))
    }

    return planetsWithCount.values.sum()
}

data class OrbitElement(var planet: String, var planetInOrbit: String)

fun calculateIndirectOrbitCount(planet: String, map: List<OrbitElement>, indirectOrbitCount: Int): Int {

    val nextOrbitElement = map.filter { it.planetInOrbit.equals(planet) }

    if (nextOrbitElement.isEmpty()) {
        return indirectOrbitCount
    }

    return calculateIndirectOrbitCount(nextOrbitElement.first().planet, map, indirectOrbitCount + 1)

}


fun day6b(map: String): Int {
    val san = "SAN"
    val you = "YOU"

    val orbitElements = map.split("\n").map {
        OrbitElement(it.split(")").get(0), it.split(")").get(1))
    };

    return calculateTo(san, orbitElements, you)
}

fun calculateTo(san: String, orbitElements: List<OrbitElement>, you: String): Int {
    val sanPlanets = calculateIndirectOrbitsPlanets(san, orbitElements, arrayListOf());
    val youPlanets = calculateIndirectOrbitsPlanets(you, orbitElements, arrayListOf());
    val samePlanet = sanPlanets.filter { youPlanets.contains(it) }.first()!!;

    val count1 = sanPlanets.indexOf(samePlanet)-1;
    val count2 =  youPlanets.indexOf(samePlanet)-1;
    return count1 + count2;
}



fun calculateIndirectOrbitsPlanets(planet: String, map: List<OrbitElement>, planets: ArrayList<String>): ArrayList<String> {
    planets.add(planet)

    val nextOrbitElement = map.filter { it.planetInOrbit == planet }
    if (nextOrbitElement.isEmpty()) {
        return planets
    }

    val nextOrbitPlanet = nextOrbitElement.first().planet
    return calculateIndirectOrbitsPlanets(nextOrbitPlanet, map, planets)

}