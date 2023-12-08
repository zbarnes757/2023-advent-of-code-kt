enum class Type { FIVE, FOUR, FULL, THREE, TWO, ONE, HIGH, ERROR }

fun String.type(): Type {
    val freq = groupingBy { it }.eachCount()
    return when (freq.values.max()) {
        5 -> Type.FIVE
        4 -> Type.FOUR
        3 -> if (freq.values.size == 2) Type.FULL else Type.THREE
        2 -> if (freq.values.size == 3) Type.TWO else Type.ONE
        1 -> Type.HIGH
        else -> Type.ERROR
    }
}

fun main() {
    fun part1(input: List<String>): Int {

        val order = listOf('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2')
        val comparator = compareByDescending<Triple<String, Type, Int>> { it.second.ordinal }.thenByDescending {
            it.first.fold(1L) { acc, c ->
                acc * 13L + order.indexOf(c)
            }
        }

        return input.asSequence().map { it.split(" ") }.map { (hand, bid) ->
            val bestType = hand.type()
            Triple(hand, bestType, bid.toInt())
        }.sortedWith(comparator).withIndex().sumOf { (index, triple) -> (index + 1) * triple.third }

    }


    fun part2(input: List<String>): Int {
        val order = listOf('A', 'K', 'Q', 'T', '9', '8', '7', '6', '5', '4', '3', '2', 'J')
        val comparator = compareByDescending<Triple<String, Type, Int>> { it.second.ordinal }.thenByDescending {
            it.first.fold(1L) { acc, c ->
                acc * 13L + order.indexOf(c)
            }
        }

        return input.asSequence().map { it.split(" ") }.map { (hand, bid) ->
            val bestType = order.dropLast(1).minOf { c -> hand.replace('J', c).type() }
            Triple(hand, bestType, bid.toInt())
        }.sortedWith(comparator).withIndex().sumOf { (index, triple) -> (index + 1) * triple.third }
    }

    val input = readInput("camel_cards")
    part1(input).println()
    part2(input).println()
}