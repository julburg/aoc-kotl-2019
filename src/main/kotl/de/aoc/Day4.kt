package de.aoc


fun day4a(rangeFrom: Int, rangeUntil: Int): Int {
    return (rangeFrom..rangeUntil).filter { it.isCorrect() }.count();
}


public fun Int.isCorrect(): Boolean {
    var hasDouble = false;
    val toCharArray = this.toString().toCharArray();
    for (number in 0 until 5) {
        val currentElement = toCharArray.get(number).toInt()
        val nextElement = toCharArray.get(number + 1).toInt()
        if (currentElement > nextElement) {
            return false
        }
        if (currentElement.equals(nextElement)) {
            hasDouble = true
        }
    }
    return hasDouble;
}

fun day4b(rangeFrom: Int, rangeUntil: Int): Int {
    return (rangeFrom..rangeUntil).filter { it.isCorrectb() }.count();
}

public fun Int.isCorrectb(): Boolean {
    var hasDouble = false;
    val toCharArray = this.toString().toCharArray();
    for (number in 0 until 5) {
        val currentElement = toCharArray.get(number).toInt()
        val nextElement = toCharArray.get(number + 1).toInt()
        if (currentElement > nextElement) {
            return false
        }
        if (currentElement.equals(nextElement)) {
            if (notEqualsWithAfter(number, toCharArray, currentElement)&& notEqualsWithBevore(number, toCharArray, currentElement)) {
                hasDouble = true
            }

        }
    }
    return hasDouble;
}

private fun notEqualsWithAfter(number: Int, toCharArray: CharArray, currentElement: Int) =
        number > 3 || (number <= 3 && !toCharArray.get(number + 2).toInt().equals(currentElement))

private fun notEqualsWithBevore(number: Int, toCharArray: CharArray, currentElement: Int) =
        number == 0 || (number > 0 && !toCharArray.get(number - 1).toInt().equals(currentElement))

