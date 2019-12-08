package de.aoc


fun day8a(wide: Int, tall: Int, imageData: String): Int {
    val layers = parseLayers(imageData, wide * tall)
    val layerWithMinZeroValues = layers.sortedBy { it.countValues('0') }
    val countOne = layerWithMinZeroValues.first().countValues('1')
    val countTwo = layerWithMinZeroValues.first().countValues('2')
    return countOne * countTwo;
}

fun day8b(wide: Int, tall: Int, imageData: String): ArrayList<Char> {
    val length = wide * tall
    val layers = parseLayers(imageData, length)
    val image = ArrayList<Char>()
    for (index in 0 until length) {
        val elements = layers.map { it.getByIndex(index) }.filter { !it.equals('2') }
        if (elements.isEmpty()) {
            image.add('2')
        } else {
            image.add(elements.first())
        }
    }
    return image;
}


data class Layer(private val layerData: String) {

    fun countValues(value: Char): Int {
        return layerData.count { it == value }
    }

    fun getByIndex(index: Int): Char {
        return layerData.get(index);
    }
}

fun parseLayers(imageData: String, length: Int): List<Layer> {
    return imageData.chunked(length).map { Layer(it) }
}
