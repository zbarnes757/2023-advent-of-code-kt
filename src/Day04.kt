import java.util.*
import kotlin.math.pow

fun main() {
    val reDigit = "\\d+".toRegex()

    fun findNumbers(numbers: String): Sequence<String> {
        return reDigit.findAll(numbers).map { match -> match.value }
    }

    fun findMatches(game: String): Int {
        val (winningNumbers, numbers) = game.split("|")
        val winningNumberSet = findNumbers(winningNumbers).toSet()
        return findNumbers(numbers).count { number -> winningNumberSet.contains(number) }
    }

    fun part1(input: List<String>): Double {
        var result = 0.0
        for (line in input) {
            val (_, game) = line.split(":")
            val matches = findMatches(game)

            result = if (matches > 0) {
                result + 2.0.pow(matches - 1)
            } else {
                result + 0
            }
        }
        return result
    }


    fun part2(input: List<String>): Int {
        val previousCardMatches = mutableMapOf<Int, Int>()
        val copiesToCheck: Queue<Int> = LinkedList()
        var result = 0
        for ((idx, line) in input.withIndex()) {
            val (_, game) = line.split(":")
            var cardID = idx + 1
            var matches = findMatches(game)
            previousCardMatches[cardID] = matches
            result++
            while (matches > 0) {
                cardID++
                matches--
                copiesToCheck.add(cardID)
            }
        }


        while (copiesToCheck.size > 0) {
            result++
            var copiedCardID = copiesToCheck.remove()
            var copiesToAdd = previousCardMatches[copiedCardID]!!

            while (copiesToAdd > 0) {
                copiedCardID++
                copiesToAdd--
                copiesToCheck.add(copiedCardID)
            }
        }
        return result
    }

    val input = readInput("scratchcards")
    part1(input).println()
    part2(input).println()
}